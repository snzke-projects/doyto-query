package win.doyto.query.demo.module.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * MenuRequest
 *
 * @author f0rb
 */
@Getter
@Setter
public class MenuRequest {

    private Integer id;

    private String platform;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parentId;

    private String menuName;

    private String memo;

    private Boolean valid = true;

    public MenuIdWrapper toIdWrapper() {
        return new MenuIdWrapper(this.id, this.platform);
    }
}
