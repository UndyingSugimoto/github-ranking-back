package github_ranking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github_ranking.data.MinimumUserInfomation;
import github_ranking.data.RankByLanguage;
import github_ranking.data.User;
import github_ranking.data.UserInformationRes;
import github_ranking.data.res.RanksByLanguageRes;
import github_ranking.data.res.UserDetailRes;
import github_ranking.data.res.UserEntryRes;
import github_ranking.data.res.UserExistsRes;
import github_ranking.mapper.UserDetailMapper;
import github_ranking.mapper.response.MinimumUserInfomationEntity;
import github_ranking.mapper.response.UserDetailEntity;

@Service
public class UserInformationService {

	@Autowired
	UserDetailMapper mapper;

	private final Integer followersCountCoefficient = 1;
	private final Integer issuesCountCoefficient = 1;
	private final Integer pullRequestCountCoefficient = 2;
	private final Integer repositoriesCountCoefficient = 1;
	private final Integer forksCountTotalCoefficient = 3;
	private final Integer stargazerCountTotalCoefficient = 3;
	private final Integer watchersCountTotalCoefficient = 2;

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

		String tier = this.calcTier(user.getUserId());
		Integer rank = this.calcRank(user.getUserId());
		Integer score = this.calcScore(followersCount, issuesCount, pullRequestCount, repositoriesCount,
				forksCountTotal, stargazerCountTotal, watchersCountTotal);

		mapper.entryReqMapper(user.getUserId(), user.getUserId(), user.getAvatarUrl(), tier, rank, score,
				followersCount,
				issuesCount, pullRequestCount, repositoriesCount, forksCountTotal, stargazerCountTotal,
				watchersCountTotal, mainLanguage);

		System.out.println(req);

		UserEntryRes res = new UserEntryRes();
		res.setUserId(user.getUserId());
		return res;
	}

	public UserExistsRes userExists(String userId) {
		UserDetailEntity entity = mapper.getUserDetail(userId);
		System.out.println("userExists userId :" + userId);
		UserExistsRes response = new UserExistsRes();
		System.out.println("existsEntity :" + entity);
		if (entity == null || entity.getUser_Id() == null) {
			response.setExists(false);
		} else {
			response.setExists(true);
		}
		return response;
	}

	public UserDetailRes getUserDetail(String userId) {
		return convert(mapper.getUserDetail(userId));
	}

	private UserDetailRes convert(UserDetailEntity entity) {

		UserDetailRes res = new UserDetailRes();

		if (entity == null) {
			return res;
		}
		res.setAvatarUrl(entity.getAvatar_Url());
		res.setFollowersCount(entity.getFollowers_Count());
		res.setForksCountTotal(entity.getForks_CountTotal());
		res.setIssuesCount(entity.getIssues_Count());
		res.setMainLanguage(entity.getMain_Language());
		res.setPullRequestCount(entity.getPullRequest_Count());
		res.setRank(this.calcRank(entity.getUser_Id()));
		res.setRepositoriesCount(entity.getRepositories_Count());
		res.setScore(entity.getScore());
		res.setStargazerCountTotal(entity.getStargazer_Count_Total());
		res.setTier(this.calcTier(entity.getUser_Id()));
		res.setUserId(entity.getUser_Id());
		res.setUserName(entity.getUser_Name());
		res.setWatchersCountTotal(entity.getWatchers_Count_Total());
		res.setCurrentNumber(mapper.getUserCount().stream().count());

		return res;
	}

	private Integer calcScore(Integer followersCount, Integer issuesCount, Integer pullRequestCount,
			Integer repositoriesCount, Integer forksCountTotal, Integer stargazerCountTotal,
			Integer watchersCountTotal) {
		Integer followersCountScore = followersCount * followersCountCoefficient;
		Integer issuesCountScore = issuesCount * issuesCountCoefficient;
		Integer pullRequestCountScore = pullRequestCount * pullRequestCountCoefficient;
		Integer repositoriesCountScore = repositoriesCount * repositoriesCountCoefficient;
		Integer forksCountTotalScore = forksCountTotal * forksCountTotalCoefficient;
		Integer stargazerCountTotalScore = stargazerCountTotal * stargazerCountTotalCoefficient;
		Integer watchersCountTotalScore = watchersCountTotal * watchersCountTotalCoefficient;
		Integer totalScore = followersCountScore + issuesCountScore + pullRequestCountScore + repositoriesCountScore
				+ forksCountTotalScore + stargazerCountTotalScore + watchersCountTotalScore;

		return totalScore;
	}

	private String calcTier(String userId) {
		List<String> entities = mapper.getUserCount();
		int rank = entities.indexOf(userId) + 1;
		int totalCount = entities.size();
		double challengerCheck = totalCount * 0.05;
		if (rank <= challengerCheck) {
			return "Challenger";
		}

		double masterCheck = totalCount * 0.10;
		if (rank <= masterCheck) {
			return "Master";
		}

		double diamondCheck = totalCount * 0.10;
		if (rank <= diamondCheck) {
			return "Diamond";
		}

		double pratinumCheck = totalCount * 0.10;
		if (rank <= pratinumCheck) {
			return "Pratinum";
		}

		double goldCheck = totalCount * 0.10;
		if (rank <= goldCheck) {
			return "Gold";
		}

		double silverCheck = totalCount * 0.10;
		if (rank <= silverCheck) {
			return "Silver";
		}

		return "Bronze";
	}

	private Integer calcRank(String userId) {
		List<String> entities = mapper.getUserCount();
		int rank = entities.indexOf(userId) + 1;
		return rank;
	}

	public RanksByLanguageRes getRanksByLanguage() {
		RanksByLanguageRes res = new RanksByLanguageRes();
		res.setRankByLanguages(new ArrayList<RankByLanguage>());
		System.out.println("getUserListOrderByRank :" + this.mapper.getUserListOrderByRank());
		res.getRankByLanguages().add(new RankByLanguage("General",
				convert(this.mapper.getUserListOrderByRank())));
		res.getRankByLanguages().add(new RankByLanguage("JavaScript",
				convert(this.mapper.getUserListOrderByLanguageRank("JavaScript"))));
		res.getRankByLanguages().add(new RankByLanguage("Python",
				convert(this.mapper.getUserListOrderByLanguageRank("Python"))));
		res.getRankByLanguages().add(new RankByLanguage("Java",
				convert(this.mapper.getUserListOrderByLanguageRank("Java"))));
		res.getRankByLanguages().add(new RankByLanguage("C#",
				convert(this.mapper.getUserListOrderByLanguageRank("C#"))));
		res.getRankByLanguages().add(new RankByLanguage("PHP",
				convert(this.mapper.getUserListOrderByLanguageRank("PHP"))));
		res.getRankByLanguages().add(new RankByLanguage("C++",
				convert(this.mapper.getUserListOrderByLanguageRank("C++"))));
		res.getRankByLanguages().add(new RankByLanguage("Ruby",
				convert(this.mapper.getUserListOrderByLanguageRank("Ruby"))));
		res.getRankByLanguages().add(new RankByLanguage("Go",
				convert(this.mapper.getUserListOrderByLanguageRank("Go"))));

		return res;
	}

	private List<MinimumUserInfomation> convert(List<MinimumUserInfomationEntity> list) {
		List<MinimumUserInfomation> res = list.stream().map(t -> {
			MinimumUserInfomation after = new MinimumUserInfomation();
			after.setAvatarUrl(t.getAvatar_url());
			after.setMainLanguage(t.getMain_language());
			after.setScore(t.getScore());
			after.setUserId(t.getUser_id());
			return after;
		}).collect(Collectors.toList());
		return res;
	}

}
