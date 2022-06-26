package fr.florent.mjmaker.service.repository;

import java.util.Map;
import java.util.stream.Collectors;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.markdown.MarkDownService;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.EntityVar;
import fr.florent.mjmaker.utils.DataBaseUtil;

/**
 * Entity service repository
 */
@Injectable
public class EntityService extends AbstractRepository<Entity, Integer> {

    private final MarkDownService markDownService = MarkDownService.getInstance();

    private EntityService() {
        super();
    }

    public String renderEntity(Entity entity) {

        Map<String, String> values = DataBaseUtil.convertForeignCollectionToList(entity.getLstVar()).stream()
                .collect(Collectors.toMap(e -> e.getTemplateVar().getLabel(), EntityVar::getValue));

        values.put("name", entity.getName());
        values.put("level", entity.getLevel());
        values.put("template_name", entity.getTemplate().getName());

        String template = entity.getTemplate().getText();

        template = markDownService.processVariable(values, template);

        return markDownService.parseMarkDown(template);

    }

    @Override
    public Class<Entity> getTableClass() {
        return Entity.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return EntityService.class.getName();
    }
}
