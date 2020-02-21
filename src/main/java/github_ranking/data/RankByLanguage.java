package github_ranking.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankByLanguage {
	private String language;
	private List<MinimumUserInfomation> userInfomations;
}
