package com.xdai.challenge.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xdai.challenge.R
import com.xdai.challenge.models.MovieReview
import kotlinx.android.synthetic.main.movie_review_item.view.*


class MovieReviewListAdapter(private val context: Context, private val movieReviewList: List<MovieReview>?) : RecyclerView.Adapter<MovieReviewListAdapter.MovieReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieReviewViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_review_item, parent, false)
        return MovieReviewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return when {
            movieReviewList == null -> 0
            else -> movieReviewList.size
        }
    }

    override fun onBindViewHolder(holder: MovieReviewViewHolder?, position: Int) {
        val movieReview = movieReviewList!![position]
        holder?.bindItem(context, movieReview)
    }

    class MovieReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.movie_image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val headline: TextView = itemView.findViewById(R.id.headline)
        private val byline: TextView = itemView.findViewById(R.id.byline)
        private val shortSummary: TextView = itemView.findViewById(R.id.short_summary)
        private val rating: TextView = itemView.findViewById(R.id.rating)
        private val publicationDate: TextView = itemView.findViewById(R.id.publication_date)

        fun bindItem(context: Context, movieReview: MovieReview) {
            title.text = movieReview.displayTitle
            headline.text = movieReview.headline
            byline.text = movieReview.byline
            shortSummary.text = movieReview.shortSummary
            rating.text = movieReview.rating
            publicationDate.text = movieReview.publishedAt
            if (movieReview.multimedia?.src != null)
                Glide.with(context).load(movieReview.multimedia.src).into(imageView)
        }
    }
}