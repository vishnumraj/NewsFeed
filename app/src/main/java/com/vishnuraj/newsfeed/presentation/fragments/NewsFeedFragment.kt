package com.vishnuraj.newsfeed.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vishnuraj.newsfeed.R
import com.vishnuraj.newsfeed.application.NewsFeedApplication
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.NewsFeedState
import com.vishnuraj.newsfeed.presentation.viewmodels.NewsFeedViewModel
import kotlinx.android.synthetic.main.fragment_news_feed.*
import javax.inject.Inject

class NewsFeedFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var viewModel: NewsFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_news_feed, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as NewsFeedApplication).component.inject(this)
        viewModel = viewModelProvider.create(NewsFeedViewModel::class.java)
        viewModel.newsFeedState.observe(this, Observer(::onNewsFeedStateChange))
        viewModel.fetchNewsFeed()
    }

    private fun onNewsFeedStateChange(state: NewsFeedState) {
        when (state) {
            is NewsFeedState.Loading -> {
                showProgress(true)
            }
            is NewsFeedState.NewsFeedData -> {
                showNewsFeed(state.data)
            }
            is NewsFeedState.ErrorData -> {
                showError(state.error)
            }
        }
    }

    private fun showError(error: Error) {
        showProgress(false)
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(error.message)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }
    }

    private fun showNewsFeed(response: NewsFeedResponse) {
        showProgress(false)
        activity?.title = response.title
        //TODO Update the ListView

    }

    private fun showProgress(show: Boolean) {
        refreshLayout.isRefreshing = show
    }

    override fun onRefresh() {
        viewModel.fetchNewsFeed()
    }
}