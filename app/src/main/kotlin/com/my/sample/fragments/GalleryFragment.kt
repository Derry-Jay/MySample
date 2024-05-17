package com.my.sample.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.sample.adapters.PicturesAdapter
import com.my.sample.databinding.GalleryFragmentBinding
import com.my.sample.extensions.assetFiles
import com.my.sample.extensions.firstLetterCapitalized
import com.my.sample.models.Picture
import java.io.InputStream

class GalleryFragment : Fragment() {
    private var _binding: GalleryFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = GalleryFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Pictures"

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        val data: List<Picture> = view.assetFiles("imgs")?.map { s ->
            // get image path
            val pic: String = s
            val ls = pic.split("-", "_", ".")
            val nl = ls.subList(0,if (ls.size > 2) 2 else 1)
                .joinToString(" ") { it.firstLetterCapitalized }
            // get input stream
            val ims: InputStream? = context?.assets?.open("imgs/$pic")
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
                ?: Drawable.createFromPath("assets/icon/loader.gif")
            // set image to ImageView
            ims?.close()
            Picture(d!!,nl)
        } ?: listOf()

        binding.recyclerview.adapter = PicturesAdapter(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}