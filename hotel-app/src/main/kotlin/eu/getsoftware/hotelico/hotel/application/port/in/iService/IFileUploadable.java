package eu.getsoftware.hotelico.hotel.application.port.in.iService;


/**
 * Allow to upload files/images for this objects.
 * 
 */
public interface IFileUploadable
{
	long getId();
	
	String getPlainFilePath(final int upperObjectId);
	
	void setMediaUploaded(final boolean mediaUploaded);
}
