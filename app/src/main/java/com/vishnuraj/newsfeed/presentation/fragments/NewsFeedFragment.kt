package com.vishnuraj.newsfeed.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vishnuraj.newsfeed.R
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.NewsFeedState
import com.vishnuraj.newsfeed.databinding.FragmentNewsFeedBinding
import com.vishnuraj.newsfeed.presentation.adapters.NewsFeedAdapter
import com.vishnuraj.newsfeed.presentation.viewmodels.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for listing all the NewsFeed. The class observe for NewsFeedState
 * change and update the UI accordingly. The class also implements the Swipe
 * OnRefreshListener to initiate the NewsFeed request again.
 */
@AndroidEntryPoint
class NewsFeedFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        private const val NEWS_FEED_KEY = "news_feed_key"
    }

    private val viewModel: NewsFeedViewModel by viewModels()
    private var viewBinding: FragmentNewsFeedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsFeedBinding.inflate(inflater,container, false)
        return viewBinding?.root
    }

    /**
     * Save the NewsFeed change in case of Orientation change to the out state Bundle. This
     * outState Bundle will be provided in onActivityCreated method in case of app restart due
     * to configuration change.
     *
     * @param outState Bundle where the data needs to be saved
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (viewModel.newsFeedState.value as? NewsFeedState.NewsFeedData)?.data?.let {
            outState.putSerializable(NEWS_FEED_KEY, it)
        }
    }

    /**
     * This method initialise the NewsFeedViewModel and initiate the data NewsFeed fetching
     * from the server.
     *
     * @param savedInstanceState The savedInstance Bundle
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsFeedState.observe(viewLifecycleOwner, Observer(::onNewsFeedStateChange))
        fetchNewsFeed(savedInstanceState)
        viewBinding?.refreshLayout?.setOnRefreshListener(this)
    }

    /**
     * Method for handling the NewsFeedState change. The refresh layout progress
     * status and the Recycler view is updated based on the state change.
     *
     * @param state NewsFeedState
     */
    private fun onNewsFeedStateChange(state: NewsFeedState) {
        when (state) {
            is NewsFeedState.Loading -> showProgress(true)
            is NewsFeedState.NewsFeedData -> showNewsFeed(state.data)
            is NewsFeedState.ErrorData -> showError(state.error)
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
            viewBinding?.recyclerView?.layoutManager = LinearLayoutManager(context)
            viewBinding?.recyclerView?.adapter = NewsFeedAdapter(it, response.newsList)
        }
    }

    private fun showProgress(show: Boolean) {
        viewBinding?.refreshLayout?.isRefreshing = show
    }

    /**
     * Method to fetch the NewsFeed. A check is done to see if the NewsFeedResponse is
     * saved because of configuration change. if yes the NewsFeedState is updated with
     * saved data else The viewModel initiates request to fetch the NewsFeed data from
     * the server.
     *
     * @param savedInstanceState The Bundle instance saved in onSaveInstanceState
     */
    private fun fetchNewsFeed(savedInstanceState: Bundle?){
        (savedInstanceState?.getSerializable(NEWS_FEED_KEY) as?  NewsFeedResponse)?.let {
            viewModel.newsFeedState.value = NewsFeedState.NewsFeedData(it)
        }?:run {
            viewModel.fetchNewsFeed()
        }
    }

    override fun onRefresh() {
        viewModel.fetchNewsFeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}