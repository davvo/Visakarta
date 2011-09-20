<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0; padding: 0 }
  #map_canvas { height: 100% }
</style>
<script type="text/javascript"
    src="http://maps.googleapis.com/maps/api/js?sensor=false">
</script>
<script type="text/javascript">

  function initialize() {
    var myOptions = {
      zoom: ${map.zoom},
      center: new google.maps.LatLng(${map.center.lat}, ${map.center.lon}),
      disableDefaultUI: true,      
      <#list map.controls as control>
          <#if control == "NAVIGATION">
            panControl: true,
            zoomControl: true,
          </#if>
          <#if control == "MAP_TYPE">
            mapTypeControl: true,
          </#if>
      </#list>
      <#if map.mapType == "Normal">
          mapTypeId: google.maps.MapTypeId.ROADMAP
      </#if>
      <#if map.mapType == "Satellite">
          mapTypeId: google.maps.MapTypeId.SATELLITE
      </#if>
      <#if map.mapType == "Hybrid">
          mapTypeId: google.maps.MapTypeId.HYBRID
      </#if>
      <#if map.mapType == "Terrain">
          mapTypeId: google.maps.MapTypeId.TERRAIN
      </#if>
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"),
        myOptions);
        
    <#list map.markers as marker>
      var marker${marker.id} = new google.maps.Marker({
          position: new google.maps.LatLng(${marker.pos.lat},${marker.pos.lon}), 
          map: map
      });
      <#if marker.infoWindow>
        var infoWindow${marker.id} = new google.maps.InfoWindow({
            content: "${marker.infoWindowContentEscaped}"
        });
        
        google.maps.event.addListener(marker${marker.id}, 'click', function() {
          infoWindow${marker.id}.open(map, marker${marker.id});
        });        
      </#if>
    </#list>        
        
  }

</script>
<title>${map.title}</title>
</head>
<body onload="initialize()">
  <div id="map_canvas" style="width:100%; height:100%"></div>
</body>
</html>