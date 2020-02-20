package github_ranking.data;

import lombok.Data;

@Data
public class Node {
	String id;
	String name;
	String url;
	Language primaryLanguage;
	Forks forks;
	Wachers watchers;
	Boolean viewerHasStarred;
	Stargazers stargazers;
}
