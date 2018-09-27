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
import com.xdai.challenge.models.MovieReview
import kotlinx.android.synthetic.main.main_fragment.*
import com.xdai.challenge.R
import com.xdai.challenge.controls.EndlessScrollManager

import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var factory: MainViewModelFactory
    private var adapter: MovieReviewListAdapter? = null
    private var viewModel: com.xdai.challenge.ui.main.MainViewModel? = null
    private var movieReviewList: MutableList<MovieReview> = arrayListOf()
    private var endlessScrollManager: EndlessScrollManager? = null
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeRecyclerView();
        (activity as MainActivity).component.inject(this)

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java!!)
        loadMovieReviews(0)

        viewModel!!.movieReviewsResult().observe(this, Observer {
            error_message.visibility = View.GONE
            progress_bar.visibility = View.GONE
            if (swipe_refresh_layout.isRefreshing)
                swipe_refresh_layout.isRefreshing = false

            if (it != null && it.status == "OK") {
                movieReviewList.addAll(it.results)
                adapter!!.notifyItemInserted(adapter!!.itemCount)
                bindEndlessScrollManager(it.hasMore)
            } else if (it == null || it.status != "OK") {

            }
        })

        viewModel!!.movieReviewsError().observe(this, Observer {
            Log.e(LOG, "error: $it")
            error_message.text = getString(R.string.error_message)
            progress_bar.visibility = View.GONE
            error_message.visibility = View.VISIBLE
        })
    }

    private fun initializeRecyclerView() {
        adapter = MovieReviewListAdapter(activity!!, movieReviewList)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        swipe_refresh_layout.setOnRefreshListener {
            movieReviewList.clear()
            adapter!!.notifyDataSetChanged()
            loadMovieReviews(0)
        }
    }

    private fun bindEndlessScrollManager(hasMore: Boolean) {
        if(endlessScrollManager == null) {
            endlessScrollManager = object : EndlessScrollManager(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    loadMovieReviews(adapter?.itemCount!!)
                }
            }
            recycler_view.addOnScrollListener(endlessScrollManager)
        }
        endlessScrollManager!!.hasMore = hasMore

    }

    private fun loadMovieReviews(offset: Int) {
        viewModel!!.loadMovieReviews(getString(R.string.api_key), offset)

    }

    companion object {
        val LOG = MainFragment::class.java.canonicalName

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
