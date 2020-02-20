package github_ranking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import github_ranking.data.User;
import github_ranking.data.UserInformationRes;

@Service
public class UserInformationService {

	public void entryUserInformation(UserInformationRes req) {

		User user = req.getUser();
		Integer followersCount = user.getFollowers().getTotalCount();
		Integer issuesCount = user.getIssues().getTotalCount();
		Integer pullRequestCount = user.getPullRequests().getTotalCount();
		Integer repositoriesCount = user.getRepositories().getTotalCount();
		Integer forksCountTotal = user.getRepositories().getNodes().stream().mapToInt(t -> t.getForks().getTotalCount())
				.sum();
		Integer stargazerCountTotal = user.getRepositories().getNodes().stream().mapToInt(t -> t.getStargazers().getTotalCount())
		.sum();
		Integer watchersCountTotal = user.getRepositories().getNodes().stream().mapToInt(t -> t.getWatchers().getTotalCount())
		.sum();
		String mainLanguage = "";
		long maxLangCount = 0;
		List<String> distinctLanguageList = user.getRepositories().getNodes().stream().map(t -> {
		if(t.getPrimaryLanguage() != null) {
			return t.getPrimaryLanguage().getName();
		}else {
			return "nonlang";
		}
		}
		).distinct().collect(Collectors.toList());
		for(String lang :distinctLanguageList) {
			long currentCount  = user.getRepositories().getNodes().stream().map(t -> t.getPrimaryLanguage().getName()).filter(t -> t.equals(lang)).count();
			if (currentCount > maxLangCount) {
				maxLangCount = currentCount;
				mainLanguage = lang;
			}
		}

		System.out.println(req);
	}

}
