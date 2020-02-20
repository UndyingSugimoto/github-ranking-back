package github_ranking.data;

import lombok.Data;

@Data
public class User {
	String userId;
	String avatarUrl;
	Repositories repositories;
	Followers followers;
	PullRequests pullRequests;
	Issues issues;
}
