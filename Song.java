/*
  * This project involves implementing a Java program that builds and manages a database of songs using
a GUI-based user interface implemented with JavaFX.
The application will consist of a single frame that allows a user to add, edit, or delete songs from the
database. Here’s a rough prototype for what the window might look like. Yours doesn’t have to look
exactly like this…the prototype is provided just to help you envision the ultimate application. However
a neat and reasonable-looking GUI is expected.
 */

/*
 * Author: Emanuel Calderon
 */

public class Song
{
	private String songNameDefault = "--Select Song==";
	private String songName;
	private String songItemCode;
	private String songDescription;
	private String songArtist;
	private String songAlbum;
	private double songPrice;
	
	public String toString()
	{
		return songName;
	}
	
	//constructor def
	public Song()
	{
		this.songName = "";
		this.songItemCode = "";
		this.songDescription = "";
		this.songArtist = "";
		this.songAlbum = "";
		this.songPrice = 0.00;
	}
	
	public Song(String songName, String songItemCode, String songDescription, String songArtist,
			String songAlbum, double songPrice)
	{
		this.songName = songName;
		this.songItemCode = songItemCode;
		this.songDescription = songDescription;
		this.songArtist = songArtist;
		this.songAlbum = songAlbum;
		this.songPrice = songPrice;
	}	
	
	public Double getSongPrice()
	{
		return songPrice;
	}
	public String getSongAlbum()
	{
		return songAlbum;
	}
	public String getSongArtist()
	{
		return songArtist;
	}
	public String getSongDescription()
	{
		return songDescription;
	}
	public String getSongItemCode()
	{
		return songItemCode;
	}
	public String getSongName()
	{
		return songName;
	}
	public String getSongNameDefault()
	{
		return songName;
	}
	public void setSongNameDefault(String songNameDefault)
	{
		this.songNameDefault=songNameDefault;
	}
	public void setSongName(String songName)
	{
		this.songName=songName;
	}
	public void setSongItemCode(String songItemCode)
	{
		this.songItemCode=songItemCode;
	}
	public void setSongDescription(String songDescription)
	{
		this.songDescription=songDescription;
	}
	public void setSongArtist(String songArtist)
	{
		this.songArtist=songArtist;
	}
	public void setSongAlbum(String songAlbum)
	{
		this.songAlbum=songAlbum;
	}
	public void setSongPrice(double songPrice)
	{
		this.songPrice=songPrice;
	}
}
