package blog.platform.domain.Article;

import blog.platform.domain.Hashtag;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ArticleDeserializer extends JsonDeserializer<Article> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Article deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Article article = new Article();

        article.setTitle(node.get("title").asText());
        article.setContent(node.get("content").asText());
        article.setPreview(node.get("preview").asText());
        //article.setContentPreview(node.get("contentPreview").asText());

        List<ArticleHashtag> articleHashtags = new ArrayList<>();
        ArrayNode hashtagsNode = (ArrayNode) node.get("hashtags");
        if (hashtagsNode != null) {
            for (JsonNode hashtagNode : hashtagsNode) {
                String hashtagValue = hashtagNode.asText();
                ArticleHashtag articleHashtag = new ArticleHashtag();
                articleHashtag.setHashtag(new Hashtag(hashtagValue));
                articleHashtags.add(articleHashtag);
            }
        }
        article.setArticleHashtags(articleHashtags);
        return article;
    }
}
