package github_ranking.data;

import lombok.Data;

@Data
public class User {
	Repositories repositories;
	Followers followers;
	PullRequests pullRequests;
	Issues issues;
}