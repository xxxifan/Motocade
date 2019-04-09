package com.xxxifan.motorcade.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.MapView
import com.xxxifan.motorcade.R

class AmapFragment : Fragment(), AmapContract.View {

  private lateinit var mapView: MapView
  private lateinit var presenter: AmapContract.Presenter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_map, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mapView = view.findViewById(R.id.map)
    mapView.onCreate(savedInstanceState)
    presenter = AmapPresenter(mapView, lifecycle, this)
    presenter.enableLocation()
    presenter.startLocation()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}