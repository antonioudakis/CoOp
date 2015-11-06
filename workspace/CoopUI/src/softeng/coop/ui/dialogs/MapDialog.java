package softeng.coop.ui.dialogs;

import java.util.Locale;

import org.vaadin.vol.*;
import org.vaadin.vol.VectorLayer.DrawingMode;
import org.vaadin.vol.VectorLayer.VectorDrawnEvent;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.IOkCancelView;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ViewEvent;

public class MapDialog
	extends CoopDialog<BeanItem<GeoLocation>>
{
	private static final long serialVersionUID = 1L;
	
	private OpenLayersMap mapComponent;
	
	private Point point;
	
	private MarkerLayer markerLayer;
	
	private Marker marker;
	
	public MapDialog()
	{
		this.setWidth("680px");
		this.setHeight("520px");
		this.setModal(true);
	}

	@Override
	public void dataBind()
	{
		if (this.getModel() == null) return;
		
		GeoLocation geoLocation = this.getModel().getBean();
		
		if (geoLocation == null) return;
		
		point = 
			new Point(
					geoLocation.getLongtitude(), 
					geoLocation.getLatitude());
		
		mapComponent.setCenter(point.getLon(), point.getLat());
		mapComponent.setZoom(16);
		
		marker = new Marker(point.getLon(), point.getLat());
		
		markerLayer.addMarker(marker);
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		this.getOkCancelView().setReadOnly(this.isReadOnly());
		
		mapComponent = new OpenLayersMap();
		
		mapComponent.setSizeFull();
//		mapComponent.setWidth("100%");
//		mapComponent.setHeight("300px");
		
		mapComponent.addLayer(new GoogleStreetMapLayer());
		mapComponent.addLayer(new GoogleSatelliteMapLayer());
		mapComponent.addLayer(new GoogleHybridMapLayer());
		mapComponent.addLayer(new GoogleTerrainMapLayer());
		
		markerLayer = new MarkerLayer();
		mapComponent.addLayer(markerLayer);
		
		if (!this.isReadOnly())
		{
			final VectorLayer vectorLayer = new VectorLayer();
			
			vectorLayer.setDrawindMode(DrawingMode.POINT);
			
			this.mapComponent.addLayer(vectorLayer);
			
			vectorLayer.addListener(new VectorLayer.VectorDrawnListener()
			{
				@Override
				public void vectorDrawn(VectorDrawnEvent event)
				{
					if (event.getVector() instanceof PointVector)
					{
						if (marker != null)
						{
							PointVector pointVector = (PointVector)event.getVector();
							
							point = pointVector.getPoint();
							
							marker.setLon(point.getLon());
							marker.setLat(point.getLat());
							
							vectorLayer.removeComponent(pointVector);
						}
					}
				}
			});
			
		}
		
		this.addComponent(mapComponent);

		getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				BeanItem<GeoLocation> item = getModel();
				
				if (item == null) return;
				
				item.getItemProperty("longtitude").setValue(point.getLon());
				item.getItemProperty("latitude").setValue(point.getLat());
			}
		});
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}
	
}
