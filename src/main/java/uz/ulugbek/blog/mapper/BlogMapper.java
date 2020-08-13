package uz.ulugbek.blog.mapper;

import org.mapstruct.*;
import uz.ulugbek.blog.domain.Blog;
import uz.ulugbek.blog.dto.BlogDTO;

/**
 * Mapper for the entity {@link Blog} and its DTO {@link BlogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {



    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
