package ca.mcgill.ecse321.TMS.dto;

import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Tree;

public class TreeLocationDto {
	public enum LandUseType { Residential, Institutional, Park, Municipal }
	private LandUseType landUseType;
	private int longitude;
	private int latitude;
	private Municipality municipality;
	private Tree tree;
	
	public TreeLocationDto() {
		
	}
	
	public TreeLocationDto(LandUseType landUseType, int longitude, int latitude, Municipality municipality, Tree tree) {
		this.landUseType = landUseType; 
		this.longitude = longitude; 
		this.latitude = latitude; 
		this.municipality = municipality;
		this.tree = tree;
	}

	public LandUseType getLandUseType() {
		return landUseType;
	}

	public void setLandUseType(LandUseType landUseType) {
		this.landUseType = landUseType;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public Municipality getMunicipality() {
		return municipality;
	}

	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
	
}
