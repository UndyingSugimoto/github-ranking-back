package github_ranking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github_ranking.data.User;
import github_ranking.data.UserInformationRes;
import github_ranking.data.res.UserDetailRes;
import github_ranking.data.res.UserEntryRes;
import github_ranking.data.res.UserExistsRes;
import github_ranking.mapper.UserDetailMapper;
import github_ranking.mapper.response.UserDetailEntity;

@Service
public class UserInformationService {

	@Autowired
	UserDetailMapper mapper;

	public UserEntryRes entryUserInformation(UserInformationRes req) {

		User user = req.getUser();
		Integer followersCount = user.getFollowers().getTotalCount();
		Integer issuesCount = user.getIssues().getTotalCount();
		Integer pullRequestCount = user.getPullRequests().getTotalCount();
		Integer repositoriesCount = user.getRepositories().getTotalCount();
		Integer forksCountTotal = user.getRepositories().getNodes().stream().mapToInt(t -> t.getForks().getTotalCount())
				.sum();
		Integer stargazerCountTotal = user.getRepositories().getNodes().stream()
				.mapToInt(t -> t.getStargazers().getTotalCount())
				.sum();
		Integer watchersCountTotal = user.getRepositories().getNodes().stream()
				.mapToInt(t -> t.getWatchers().getTotalCount())
				.sum();
		String mainLanguage = "";
		long maxLangCount = 0;
		List<String> distinctLanguageList = user.getRepositories().getNodes().stream().map(t -> {
			if (t.getPrimaryLanguage() != null) {
				return t.getPrimaryLanguage().getName();
			} else {
				return "nonlang";
			}
		}).distinct().collect(Collectors.toList());
		for (String lang : distinctLanguageList) {
			long currentCount = user.getRepositories().getNodes().stream().map(t -> {
				if (t.getPrimaryLanguage() != null) {
					return t.getPrimaryLanguage().getName();
				} else {
					return "nonlang";
				}
			})
					.filter(t -> t.equals(lang)).count();
			if (currentCount > maxLangCount) {
				maxLangCount = currentCount;
				mainLanguage = lang;
			}
		}

		String tier = "";
		Integer rank = 0;
		Integer score = 0;
		Integer currentNumber = 0;

		mapper.entryReqMapper(user.getUserId(), user.getUserId(), tier, rank, score, followersCount,
				issuesCount, pullRequestCount, repositoriesCount, forksCountTotal, stargazerCountTotal,
				watchersCountTotal, mainLanguage);

		System.out.println(req);

		UserEntryRes res = new UserEntryRes();
		res.setUserId(user.getUserId());
		return res;
	}

	public UserExistsRes userExists(String userId) {
		UserDetailEntity entity = mapper.getUserDetail(userId);
		System.out.println("userExists userId :"+userId);
		UserExistsRes response = new UserExistsRes();
		System.out.println("existsEntity :"+entity);
		if(entity == null || entity.getUser_Id() == null) {
			response.setExists(false);
		}else {
			response.setExists(true);
		}
		return response;
	}


	public UserDetailRes getUserDetail(String userId) {
		return  convert(mapper.getUserDetail(userId));
	}

	private UserDetailRes convert(UserDetailEntity entity) {

		UserDetailRes res = new UserDetailRes();

		if (entity == null) {
			return res;
		}
		res.setFollowersCount(entity.getFollowers_Count());
		res.setForksCountTotal(entity.getForks_CountTotal());
		res.setIssuesCount(entity.getIssues_Count());
		res.setMainLanguage(entity.getMain_Language());
		res.setPullRequestCount(entity.getPullRequest_Count());
		res.setRank(entity.getRank());
		res.setRepositoriesCount(entity.getRepositories_Count());
		res.setScore(entity.getScore());
		res.setStargazerCountTotal(entity.getStargazer_Count_Total());
		res.setTier(entity.getTier());
		res.setUserId(entity.getUser_Id());
		res.setUserName(entity.getUser_Name());
		res.setWatchersCountTotal(entity.getWatchers_Count_Total());



		return res;
	}
}
