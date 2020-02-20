package github_ranking.data.res;

import lombok.Data;

@Data
public class UserDetailRes {
	String userName;
	String userId;
	String avatarUrl;
	String tier;
	Integer rank;
	Integer score;
	Long currentNumber;
	Integer followersCount;
	Integer issuesCount;
	Integer pullRequestCount;
	Integer repositoriesCount;
	Integer forksCountTotal;
	Integer stargazerCountTotal;
	Integer watchersCountTotal;
	String mainLanguage;
}
