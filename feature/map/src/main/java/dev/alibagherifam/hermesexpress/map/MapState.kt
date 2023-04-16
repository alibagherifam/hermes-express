package dev.alibagherifam.hermesexpress.map

import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager

// TODO: Remove this class after knowing WTF is remember() function!
class MapState {
    var userCoordinates: Point? = null
    var markerManager: CircleAnnotationManager? = null
}
