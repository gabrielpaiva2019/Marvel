package com.paiva.marvel.screens.heroesList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.paiva.marvel.R
import com.paiva.marvel.model.Heroes
import com.paiva.marvel.screens.error.ErrorDialogFragment
import com.paiva.marvel.screens.loading.LoadingDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Math.abs

class HeroesActivity : AppCompatActivity() {
    private val viewModel by viewModel<HeroesViewModel>()

    private lateinit var loadingDialogFragment: LoadingDialogFragment
    private lateinit var errorDialogFragment: ErrorDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configScreen()
    }

    private fun configScreen() {
        initializeVars()
        setupObservers()
        callMarvelApi()
    }

    private fun initializeVars() {
      val errorDialogBuilder = ErrorDialogFragment
            .Builder()
            .addButtonClick { callMarvelApi() }

        errorDialogFragment = ErrorDialogFragment(errorDialogBuilder)
        loadingDialogFragment = LoadingDialogFragment()

    }

    private fun callMarvelApi() {
        loadingDialogFragment.show(supportFragmentManager, LOADING_FRAGMENT_TAG)
        viewModel.fetchHeroes()
    }

    private fun setupObservers() {
        viewModel.error.observe(this, { isError ->
            if(isError){
                configErrorScreen()
            }
        })

        viewModel.heroes.observe(this@HeroesActivity, { heroes ->
            loadingDialogFragment.dismiss()
            configRecyclerView(heroes)
            configViewPager(heroes)
        })
    }

    private fun configErrorScreen() {
        errorDialogFragment.show(supportFragmentManager, ERROR_FRAGMENT_TAG)
        loadingDialogFragment.dismiss()
    }

    private fun configRecyclerView(heroes: Heroes) {
        var gridLayoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        val adapter = HeroesAdapter(heroes.data.results)
        recyclerListHeroes.layoutManager = gridLayoutManager
        recyclerListHeroes.addItemDecoration(HeroesRecyclerViewDecoration(GRID_SPAN_COUNT, GRID_SPACING, true))
        recyclerListHeroes.adapter = adapter
    }

    private fun configViewPager(heroes: Heroes) {
        val viewPagerAdapter = HeroesViewPagerAdapter(heroes.data.results)

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        val itemDecoration = ViewPagerDecoration(
            this,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPagerFiveFirstHeroes.setPageTransformer(pageTransformer)
        viewPagerFiveFirstHeroes.addItemDecoration(itemDecoration)
        viewPagerFiveFirstHeroes.adapter = viewPagerAdapter
        viewPagerFiveFirstHeroes.currentItem = FIRST_POSITION_VIEW_PAGER
        viewPagerFiveFirstHeroes.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT

        TabLayoutMediator(viewPagerTabs, viewPagerFiveFirstHeroes) { _, _ -> }.attach()
    }

    companion object {
        const val LOADING_FRAGMENT_TAG = "LOADING"
        const val ERROR_FRAGMENT_TAG = "ERROR"
        const val FIRST_POSITION_VIEW_PAGER = 1
        const val OFFSCREEN_PAGE_LIMIT = 2
        const val GRID_SPAN_COUNT = 3
        const val GRID_SPACING = 50
    }
}