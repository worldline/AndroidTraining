package worldline.ssm.rd.ux.wltwitter.pojo;

import com.google.gson.annotations.SerializedName;

public class TwitterUser {

	@SerializedName("screen_name")
	public String screenName;

	@SerializedName("name")
	public String name;

	@SerializedName("profile_image_url")
	public String profileImageUrl;

}
