package github_ranking.data.res;

import lombok.Data;

@Data
public class UserDetailRes {
	String userName;
	String userId;
	String tier;
	Integer rank;
	Integer score;
	Integer currentNumber;
	Integer followersCount;
	Integer issuesCount;
	Integer pullRequestCount;
	Integer repositoriesCount;
	Integer forksCountTotal;
	Integer stargazerCountTotal;
	Integer watchersCountTotal;
	String mainLanguage;
}
