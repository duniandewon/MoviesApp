package com.example.moviesapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.moviesapp.R
import com.example.moviesapp.adapters.CarouselAdapter
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.utils.BoundsOffsetDecoration
import com.example.moviesapp.utils.HorizontalSpaceItemDecoration
import com.example.moviesapp.viewmodel.HomeViewModel
import kotlin.math.abs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var snapHelper: SnapHelper

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]



        homeViewModel.observePopularMovies()
            .observe(
                viewLifecycleOwner
            ) {
                layoutManager = ProminentLayoutManager(context)
                carouselAdapter = CarouselAdapter(it.results)
                snapHelper = PagerSnapHelper()

                binding.rcvCarousel.layoutManager = layoutManager
                binding.rcvCarousel.adapter = carouselAdapter

                val spacing = resources.getDimensionPixelOffset(R.dimen.horizontal_gutter_width)

                binding.rcvCarousel.addItemDecoration(HorizontalSpaceItemDecoration(spacing))
                binding.rcvCarousel.addItemDecoration(BoundsOffsetDecoration())

                snapHelper.attachToRecyclerView(binding.rcvCarousel)
            }
    }

    internal class ProminentLayoutManager(
        context: Context?,
        private val minScaleDistanceFactor: Float = 1.5f,
        private val scaleDownBy: Float = 0.5f
    ) : LinearLayoutManager(context, HORIZONTAL, false) {
        override fun onLayoutCompleted(state: RecyclerView.State?) =
            super.onLayoutCompleted(state).also { scaleChildren() }

        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State
        ) = super.scrollHorizontallyBy(dx, recycler, state).also {
            if (orientation == HORIZONTAL) scaleChildren()
        }

        private fun scaleChildren() {
            val containerCenter = width / 2f

            val scaleDistanceThreshold = minScaleDistanceFactor * containerCenter

            for (i in 0 until childCount) {
                val child = getChildAt(i)!!

                val childCenter = (child.left + child.right) / 2f
                val distanceToCenter = abs(childCenter - containerCenter)

                val scaleDownAmount = (distanceToCenter / scaleDistanceThreshold).coerceAtMost(0.5f)
                val scale = 1f - scaleDownBy * scaleDownAmount

                child.scaleX = scale
                child.scaleY = scale


                val translationDirection = if (childCenter > containerCenter) -1 else 1
                val translationXFromScale = translationDirection * child.width * (1 - scale) / 2f
                child.translationX = translationXFromScale
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}