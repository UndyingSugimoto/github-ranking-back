package github_ranking.mapper.response;

import lombok.Data;

@Data
public class UserDetailEntity {
	String user_Name;
	String user_Id;
	String tier;
	Integer rank;
	Integer score;
	Integer followers_Count;
	Integer issues_Count;
	Integer pullRequest_Count;
	Integer repositories_Count;
	Integer forks_CountTotal;
	Integer stargazer_Count_Total;
	Integer watchers_Count_Total;
	String main_Language;
}
