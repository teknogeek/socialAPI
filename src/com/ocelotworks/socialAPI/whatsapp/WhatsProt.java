package com.ocelotworks.socialAPI.whatsapp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

abstract class WhatsProt
{
	public String CONNECTED_STATUS = "connected";                   // Describes the connection status with the WhatsApp server.
	public String DISCONNECTED_STATUS = "disconnected";             // Describes the connection status with the WhatsApp server.
	public String MEDIA_FOLDER = "media";                           // The relative folder to store received media files
	public String PICTURES_FOLDER = "pictures";                     // The relative folder to store picture files
	public int PORT = 443;                                      // The port of the WhatsApp server.
	public int TIMEOUT_SEC = 2;                                  // The timeout for the connection with the WhatsApp servers.
	public int TIMEOUT_USEC = 0;                                 //
	public String WHATSAPP_CHECK_HOST = "v.whatsapp.net/v2/exist";  // The check credentials host.
    public String WHATSAPP_GROUP_SERVER = "g.us";                   // The Group server hostname
    public String WHATSAPP_HOST = "c.whatsapp.net";                 // The hostname of the WhatsApp server.
    public String WHATSAPP_REGISTER_HOST = "v.whatsapp.net/v2/register"; // The register code host.
    public String WHATSAPP_REQUEST_HOST = "v.whatsapp.net/v2/code";      // The request code host.
    public String WHATSAPP_SERVER = "s.whatsapp.net";               // The hostname used to login/send messages.
    public String WHATSAPP_UPLOAD_HOST = "https://mms.whatsapp.net/client/iphone/upload.php"; // The upload host.
    public String WHATSAPP_DEVICE = "Android";                      // The device name.
    public String WHATSAPP_VER = "2.11.301";                // The WhatsApp version.
    public String WHATSAPP_USER_AGENT = "WhatsApp/2.11.301 Android/4.3 Device/GalaxyS3";// User agent used in request/registration code.
    
    private String accountInfo;             // The AccountInfo object.
    private String challengeFilename = "nextChallenge.dat";
    private String challengeData;           //
    private Boolean debug = false;                   // Determines whether debug mode is on or off.
    private String event;                   // An instance of the WhatsAppEvent class.
    private String[] groupList;     // An array with all the groups a user belongs in.
    private String identity;                // The Device Identity token. Obtained during registration with this API or using Missvenom to sniff from your phone.
    private String inputKey;                // Instances of the KeyStream class.
    private String outputKey;               // Instances of the KeyStream class.
    private Boolean groupId = false;         // Id of the group created.
    private Boolean lastId = false;          // Id to the last message sent.
    private String loginStatus;             // Holds the login status.
    private String[] mediaFileInfo; // Media File Information
    private String[] mediaQueue;    // Queue for media message nodes
    private int messageCounter = 1;      // Message counter for auto-id.
    private String[] messageQueue;  // Queue for received messages.
    private String name;                    // The user name.
    private Boolean newMsgBind = false;      //
    private String[] outQueue;      // Queue for outgoing messages.
    private String password;                // The user password.
    private int phoneNumber;             // The user phone number including the country code without "+" or "00".

    public WhatsProt(int number, String identity, String nickname, Boolean debug)
    {
    	this.phoneNumber = number;
    	this.debug = debug;
    	if(!checkIdentity(identity))
    	{
    		this.identity = buildIdentity(identity);
    	}
    	else
    	{
    		this.identity = identity;
    	}
    	this.name = nickname;
    	this.loginStatus = DISCONNECTED_STATUS;
    }
    
    private Boolean checkIdentity(String identity)
    {
        try
        {
			return (URLDecoder.decode(identity, "UTF-8").length() == 20) ? true : false;
		}
        catch(UnsupportedEncodingException e)
        {
			e.printStackTrace();
		}
        return false;
    }

    private String buildIdentity(String identity)
    {
        try
        {
			return URLEncoder.encode(sha1(identity.getBytes()), "UTF-8").toLowerCase();
		}
        catch (UnsupportedEncodingException e)
        {
			e.printStackTrace();
		}
        return null;
    }
    
    public static String sha1(byte[] b)
    {
    	  String result = "";
    	  for(int i = 0; i < b.length; i++)
    	  {
    	    result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
    	  }
    	  return result;
    }
}
