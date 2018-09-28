package com.xdai.challenge.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xdai.challenge.models.MovieReview
import kotlinx.android.synthetic.main.main_fragment.*
import com.xdai.challenge.R
import com.xdai.challenge.controls.EndlessScrollManager
import com.xdai.challenge.controls.NetworkManager

import javax.inject.Inject


class MainFragment : Fragment() {

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var adapter: MovieReviewListAdapter
    private lateinit var viewModel: com.xdai.challenge.ui.main.MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private var movieReviewList: MutableList<MovieReview> = arrayListOf()
    private var endlessScrollManager: EndlessScrollManager? = null
    private var isNetworkAvailable: Boolean? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).component.inject(this)

        initializeRecyclerView();

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java!!)

        viewModel.movieReviewsResult().observe(this, Observer {
            error_message.visibility = View.GONE
            swipe_refresh_layout.isRefreshing = false

            if (it != null && it.status == "OK") {
                movieReviewList.addAll(it.results)
                adapter!!.notifyItemInserted(adapter!!.itemCount)
                bindEndlessScrollManager(it.hasMore)
            } else if (it == null || it.status != "OK") {

            }
        })

        viewModel.movieReviewsError().observe(this, Observer {
            Log.e(LOG, "error: $it")
            swipe_refresh_layout.isRefreshing = false
            error_message.text = getString(R.string.error_message)

            error_message.visibility = View.VISIBLE
        })

        loadMovieReviews(0)
    }

    private fun initializeRecyclerView() {
        adapter = MovieReviewListAdapter(activity!!, movieReviewList)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        swipe_refresh_layout.setOnRefreshListener {
            movieReviewList.clear()
            adapter.notifyDataSetChanged()
            endlessScrollManager?.resetState()
            loadMovieReviews(0)
        }
    }

    private fun bindEndlessScrollManager(hasMore: Boolean) {
        if (endlessScrollManager == null) {
            endlessScrollManager = object : EndlessScrollManager(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    loadMovieReviews(adapter?.itemCount!!)
                }
            }
            recycler_view.addOnScrollListener(endlessScrollManager)
        }
        endlessScrollManager?.hasMore = hasMore
    }

    private fun loadMovieReviews(offset: Int) {
        isNetworkAvailable = NetworkManager(context!!).isOnline()
        if (isNetworkAvailable!!) {
            swipe_refresh_layout.isRefreshing = true
            viewModel.loadMovieReviews(getString(R.string.api_key), offset)
        } else {
            showNoInternetMessage()
        }

    }

    fun showNetworkStatus(isNetworkAvailable: Boolean) {
        Log.d(LOG, "showNetworkStatus new isNetworkAvailable: $isNetworkAvailable")
        Log.d(LOG, "showNetworkStatus current isNetworkAvailable: ${this.isNetworkAvailable}")

        if (this.isNetworkAvailable != isNetworkAvailable) {
            if (isNetworkAvailable && adapter.itemCount == 0) {
                swipe_refresh_layout.isRefreshing = true
                viewModel.loadMovieReviews(getString(R.string.api_key), 0)
            } else if (!isNetworkAvailable) {
                showNoInternetMessage()
            }

        }
        this.isNetworkAvailable = isNetworkAvailable
    }

    private fun showNoInternetMessage() {
        if (adapter.itemCount == 0) {
            error_message.visibility = View.VISIBLE
            swipe_refresh_layout.isRefreshing = false
            error_message.text = getString(R.string.no_internet)
        } else {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val LOG: String = MainFragment::class.java.canonicalName

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
