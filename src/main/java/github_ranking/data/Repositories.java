package github_ranking.data;

import java.util.List;

import lombok.Data;

@Data
public class Repositories {
	List<Node> nodes;
	Integer totalCount;
}
