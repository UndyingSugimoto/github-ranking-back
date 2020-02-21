package github_ranking.mapper.response;

import lombok.Data;

@Data
public class MinimumUserInfomationEntity {

	private String user_id;
	private Integer score;
	private String main_language;
	private String avatar_url;

}
