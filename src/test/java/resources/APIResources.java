package resources;

public enum APIResources {
	
	getTopRatedMovie("/movie/top_rated"),
	rateMovie("/movie");

	private String resource;
	
	APIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}
	

}
