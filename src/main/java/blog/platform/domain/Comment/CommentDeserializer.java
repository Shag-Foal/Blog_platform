package blog.platform.domain.Comment;

import blog.platform.domain.Article.Article;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CommentDeserializer extends JsonDeserializer<Comment> {
    @Override
    public Comment deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Comment comment = new Comment();
        Article article = new Article();
        article.setId(node.get("article").asLong());
        comment.setArticle(article);
        comment.setContent(node.get("content").asText());

        return comment;
    }
}
