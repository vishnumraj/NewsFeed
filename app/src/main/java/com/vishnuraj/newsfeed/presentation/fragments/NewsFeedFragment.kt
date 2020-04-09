package com.vishnuraj.newsfeed.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vishnuraj.newsfeed.R
import com.vishnuraj.newsfeed.application.NewsFeedApplication
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.NewsFeedState
import com.vishnuraj.newsfeed.presentation.adapters.NewsFeedAdapter
import com.vishnuraj.newsfeed.presentation.viewmodels.NewsFeedViewModel
import kotlinx.android.synthetic.main.fragment_news_feed.*
import javax.inject.Inject

/**
 * Fragment for listing all the NewsFeed. The class observe for NewsFeedState
 * change and update the UI accordingly. The class also implements the Swipe
 * OnRefreshListener to initiate the NewsFeed request again.
 */
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

    /**
     * This method initialise the NewsFeedViewModel and initiate the data NewsFeed fetching
     * from the server.
     *
     * @param savedInstanceState The savedInstance Bundle
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as NewsFeedApplication).component.inject(this)
        viewModel = viewModelProvider.create(NewsFeedViewModel::class.java)
        viewModel.newsFeedState.observe(this, Observer(::onNewsFeedStateChange))
        viewModel.fetchNewsFeed()
        refreshLayout.setOnRefreshListener(this)
    }

    /**
     * Method for handling the NewsFeedState change. The refresh layout progress
     * status and the Recycler view is updated based on the state change.
     *
     * @param state NewsFeedState
     */
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

    /**
     * Method for handling the Error response
     *
     * @param error Error object
     */
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

    /**
     * Method to show the News Feed in recycler view.
     *
     * @param response NewsFeedResponse object
     */
    private fun showNewsFeed(response: NewsFeedResponse) {
        showProgress(false)
        activity?.title = response.title
        context?.let {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = NewsFeedAdapter(it, response.newsList)
        }
    }

    private fun showProgress(show: Boolean) {
        refreshLayout.isRefreshing = show
    }

    override fun onRefresh() {
        viewModel.fetchNewsFeed()
    }
}