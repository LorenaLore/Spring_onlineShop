package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

/**
 * Edm provider for InitialOrderDto - used as input to create order
 */
public class ShopCreateDtoEdmProvider extends EdmProvider {

    private static final String NAMESPACE = "ro.msg.learning.shop.dto";

    static final String ENTITY_SET_NAME_INITIAL_ORDERS = "InitialOrderDTOs";
    static final String ENTITY_NAME_INITIAL_ORDER = "InitialOrderDTO";
    static final String ENTITY_SET_NAME_PRODUCTS = "ProductDTOs";
    static final String ENTITY_NAME_PRODUCT = "ProductDTO";

    private static final FullQualifiedName COMPLEX_ADDRESS_TYPE = new FullQualifiedName(NAMESPACE, "Address");

    private static final FullQualifiedName ENTITY_INITIAL_ORDER = new FullQualifiedName(NAMESPACE,
            ENTITY_NAME_INITIAL_ORDER);
    private static final FullQualifiedName ENTITY_PRODUCT = new FullQualifiedName(NAMESPACE, ENTITY_NAME_PRODUCT);

    private static final FullQualifiedName ASSOCIATION_INITIAL_ORDER_PRODUCT = new FullQualifiedName(NAMESPACE,
            "InitialOrderDTO_ProductDTOs_ProductDTO_InitialOrderDTO");

    private static final String ROLE_ORDER_PRODUCT_1 = "InitialOrderDTO_ProductDTOs";
    private static final String ROLE_ORDER_PRODUCT_2 = "ProductDTO_InitialOrderDTO";

    private static final String ENTITY_CONTAINER = "ODataCreateOrderEntityContainer";


    public EntityContainerInfo getEntityContainerInfo(String name) throws ODataException {
        if (name == null || ENTITY_CONTAINER.equals(name)) {
            return new EntityContainerInfo().setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);
        }
        return null;
    }

    public EntityType getEntityType(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (ENTITY_INITIAL_ORDER.getName().equals(edmFQName.getName())) {
                return buildInitialOrderDto();
            }
            if (ENTITY_PRODUCT.getName().equals(edmFQName.getName())) {
                return buildProductDto();
            }
        }
        return null;
    }

    private EntityType buildInitialOrderDto() {
        //Properties
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Date").setType(EdmSimpleTypeKind.DateTime)
                .setFacets(new Facets().setNullable(false).setConcurrencyMode(EdmConcurrencyMode.Fixed))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(
                                EdmTargetPath.SYNDICATION_UPDATED)));
        properties.add(
                new ComplexProperty().setName("Address").setType(new FullQualifiedName(NAMESPACE, "Address")));

        //Navigation Properties
        List<NavigationProperty> navigationProperties = new ArrayList<>();
        navigationProperties.add(new NavigationProperty().setName("ProductDTOs")
                .setRelationship(ASSOCIATION_INITIAL_ORDER_PRODUCT).setFromRole(ROLE_ORDER_PRODUCT_1).setToRole(
                        ROLE_ORDER_PRODUCT_2));

        //Key
        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_INITIAL_ORDER.getName())
                .setProperties(properties)
                .setKey(key)
                .setNavigationProperties(navigationProperties);
    }

    private EntityType buildProductDto() {
        //Properties
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
                new Facets().setNullable(false)));
        properties.add(new SimpleProperty().setName("Quantity").setType(EdmSimpleTypeKind.Int16));

        //Key
        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_PRODUCT.getName())
                .setProperties(properties)
                .setKey(key);
    }

    public ComplexType getComplexType(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (COMPLEX_ADDRESS_TYPE.getName().equals(edmFQName.getName())) {
                List<Property> properties = new ArrayList<>();
                properties.add(new SimpleProperty().setName("Country").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("City").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("County").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("Street").setType(EdmSimpleTypeKind.String));

                return new ComplexType().setName(COMPLEX_ADDRESS_TYPE.getName()).setProperties(properties);
            }
        }
        return null;
    }

    public Association getAssociation(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (ASSOCIATION_INITIAL_ORDER_PRODUCT.getName().equals(edmFQName.getName())) {
                return new Association().setName(ASSOCIATION_INITIAL_ORDER_PRODUCT.getName())
                        .setEnd1(new AssociationEnd().setType(ENTITY_INITIAL_ORDER).setRole(
                                ROLE_ORDER_PRODUCT_1).setMultiplicity(EdmMultiplicity.MANY))
                        .setEnd2(new AssociationEnd().setType(ENTITY_PRODUCT).setRole(
                                ROLE_ORDER_PRODUCT_2).setMultiplicity(EdmMultiplicity.ONE));
            }
        }
        return null;
    }

    public EntitySet getEntitySet(String entityContainer, String name) throws ODataException {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            if (ENTITY_SET_NAME_INITIAL_ORDERS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_INITIAL_ORDER);
            }
            if (ENTITY_SET_NAME_PRODUCTS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_PRODUCT);
            }
        }
        return null;
    }

    public AssociationSet getAssociationSet(String entityContainer, FullQualifiedName association,
                                            String sourceEntitySetName, String sourceEntitySetRole) throws ODataException {
        return null;
    }

    public FunctionImport getFunctionImport(String entityContainer, String name) throws ODataException {
        return null;
    }

    public List<Schema> getSchemas() throws ODataException {
        List<Schema> schemas = new ArrayList<>();
        Schema schema = new Schema();
        schema.setNamespace(NAMESPACE);

        List<EntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ENTITY_INITIAL_ORDER));
        entityTypes.add(getEntityType(ENTITY_PRODUCT));
        schema.setEntityTypes(entityTypes);


        List<ComplexType> complexTypes = new ArrayList<>();
        complexTypes.add(getComplexType(COMPLEX_ADDRESS_TYPE));
        schema.setComplexTypes(complexTypes);


        List<Association> associations = new ArrayList<>();
        associations.add(getAssociation(ASSOCIATION_INITIAL_ORDER_PRODUCT));
        schema.setAssociations(associations);

        List<EntityContainer> entityContainers = new ArrayList<>();
        EntityContainer entityContainer = new EntityContainer();
        entityContainer.setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);

        List<EntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_INITIAL_ORDERS));
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_PRODUCTS));
        entityContainer.setEntitySets(entitySets);

        entityContainers.add(entityContainer);
        schema.setEntityContainers(entityContainers);

        schemas.add(schema);
        return schemas;
    }

    public List<AliasInfo> getAliasInfos() throws ODataException {
        return null;
    }
}
