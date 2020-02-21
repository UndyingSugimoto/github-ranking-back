package github_ranking.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import github_ranking.mapper.response.MinimumUserInfomationEntity;
import github_ranking.mapper.response.UserDetailEntity;

@Mapper
public interface UserDetailMapper {
	//	@Select("SELECT id, name,stockNum FROM Stock")
	//	public List<Stock> stocklist();
	//
	@Insert("insert into user_detail (user_name, user_id , avatar_url , tier, rank , score, followers_count, issues_count ,pullrequest_count, repositories_count, forks_count_total, stargazer_count_total, watchers_count_total, main_language) values (#{userName}, #{userId},#{avatarUrl}, #{tier}, #{rank}, #{score}, #{followersCount}, #{issuesCount}, #{pullRequestCount}, #{repositoriesCount}, #{forksCountTotal}, #{stargazerCountTotal}, #{watchersCountTotal}, #{mainLanguage})")
	public void entryReqMapper(@Param("userName") String userName, @Param("userId") String userId,
			@Param("avatarUrl") String avatarUrl, @Param("tier") String tier, @Param("rank") int rank,
			@Param("score") int score, @Param("followersCount") int followersCount,
			@Param("issuesCount") int issuesCount, @Param("pullRequestCount") int pullRequestCount,
			@Param("repositoriesCount") int repositoriesCount, @Param("forksCountTotal") int forksCountTotal,
			@Param("stargazerCountTotal") int stargazerCountTotal, @Param("watchersCountTotal") int watchersCountTotal,
			@Param("mainLanguage") String mainLanguage);

	//
	//	@Delete("delete from Stock where id = #{id}")
	//	public void deleteReqMapper(@Param("id")int id);
	//
	@Select("select * from user_detail where user_id = #{user_Id}")
	public UserDetailEntity getUserDetail(@Param("user_Id") String userId);

	@Select("select user_Id from user_detail order by score desc")
	public List<String> getUserCount();

	@Select("select user_Id , main_language, score, avatar_url from user_detail where main_language = #{language} order by score desc limit 3")
	public List<MinimumUserInfomationEntity> getUserListOrderByLanguageRank(@Param("language") String language);

	@Select("select user_id , main_language, score, avatar_url from user_detail order by score desc limit 3")
	public List<MinimumUserInfomationEntity> getUserListOrderByRank();
	//
	//	@Update("update Stock set name = #{name} where id = #{id}")
	//	public void updateReqMapper(@Param("id")int id,@Param("name")String name);
	//
	//	@Update("update Stock set stockNum = stockNum + #{stockNum} where id = #{id}")
	//	public void stockChangeMapper(@Param("id")int id,@Param("stockNum")int stockNum);
	//
	//	@Update("update Stock set stockNum = #{stockNum} where id = #{id}")
	//	public void upDateStockMapper(@Param("id")int id,@Param("stockNum")int stockNum);
}
