package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

/**
 * Edm Provider for Order entity and Order linked entities
 */
public class ShopCoreProcessorEdmProvider extends EdmProvider {

    private static final String NAMESPACE = "ro.msg.learning.shop.model";

    static final String ENTITY_SET_NAME_ORDERS = "Orders";
    static final String ENTITY_NAME_ORDER = "Order";
    static final String ENTITY_SET_NAME_CUSTOMERS = "Customers";
    static final String ENTITY_NAME_CUSTOMER = "Customer";
    static final String ENTITY_SET_NAME_PRODUCTS = "Products";
    static final String ENTITY_NAME_PRODUCT = "Product";
    static final String ENTITY_SET_NAME_ORDER_DETAILS = "OrderDetails";
    static final String ENTITY_NAME_ORDER_DETAIL = "OrderDetail";

    private static final FullQualifiedName COMPLEX_ADDRESS_TYPE = new FullQualifiedName(NAMESPACE, "Address");

    private static final FullQualifiedName ENTITY_ORDER = new FullQualifiedName(NAMESPACE, ENTITY_NAME_ORDER);
    private static final FullQualifiedName ENTITY_CUSTOMER = new FullQualifiedName(NAMESPACE, ENTITY_NAME_CUSTOMER);
    private static final FullQualifiedName ENTITY_PRODUCT = new FullQualifiedName(NAMESPACE, ENTITY_NAME_PRODUCT);
    private static final FullQualifiedName ENTITY_ORDER_DETAIL = new FullQualifiedName(NAMESPACE,
            ENTITY_NAME_ORDER_DETAIL);

    private static final FullQualifiedName ASSOCIATION_ORDER_CUSTOMER = new FullQualifiedName(NAMESPACE,
            "Order_Customer_Customer_Orders");
    private static final FullQualifiedName ASSOCIATION_ORDER_DETAIL_ORDER = new FullQualifiedName(NAMESPACE,
            "OrderDetail_Order_Order_OrderDetails");
    private static final FullQualifiedName ASSOCIATION_ORDER_DETAIL_PRODUCT = new FullQualifiedName(NAMESPACE,
            "OrderDetail_Product_Product_OrderDetails");

    private static final String ROLE_ORDER_CUSTOMER_1 = "Order_Customer";
    private static final String ROLE_ORDER_CUSTOMER_2 = "Customer_Orders";
    private static final String ROLE_ORDER_DETAIL_ORDER_1 = "OrderDetail_Order";
    private static final String ROLE_ORDER_DETAIL_ORDER_2 = "Order_OrderDetails";
    private static final String ROLE_ORDER_DETAIL_PRODUCT_1 = "OrderDetail_Product";
    private static final String ROLE_ORDER_DETAIL_PRODUCT_2 = "Product_OrderDetails";

    private static final String ENTITY_CONTAINER = "ODataCreateOrderEntityContainer";


    public EntityContainerInfo getEntityContainerInfo(String name) throws ODataException {
        if (name == null || ENTITY_CONTAINER.equals(name)) {
            return new EntityContainerInfo().setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);
        }
        return null;
    }

    public EntityType getEntityType(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (ENTITY_ORDER.getName().equals(edmFQName.getName())) {
                return buildOrderEntity();
            }
            if (ENTITY_CUSTOMER.getName().equals(edmFQName.getName())) {
                return buildCustomerEntity();
            }
            if (ENTITY_PRODUCT.getName().equals(edmFQName.getName())) {
                return buildProductEntity();
            }
            if (ENTITY_ORDER_DETAIL.getName().equals(edmFQName.getName())) {
                return buildOrderDetailEntity();
            }
        }
        return null;
    }

    private EntityType buildOrderEntity() {
        //Properties
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
                new Facets().setNullable(false)));
        properties.add(
                new ComplexProperty().setName("Address").setType(new FullQualifiedName(NAMESPACE, "Address")));

        properties.add(new SimpleProperty().setName("Date").setType(EdmSimpleTypeKind.DateTime)
                .setFacets(new Facets().setNullable(false).setConcurrencyMode(EdmConcurrencyMode.Fixed))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(
                                EdmTargetPath.SYNDICATION_UPDATED)));

        //Navigation Properties
        List<NavigationProperty> navigationProperties = new ArrayList<>();
        navigationProperties.add(new NavigationProperty().setName("Customer")
                .setRelationship(ASSOCIATION_ORDER_CUSTOMER).setFromRole(ROLE_ORDER_CUSTOMER_1).setToRole(
                        ROLE_ORDER_CUSTOMER_2));
        navigationProperties.add(new NavigationProperty().setName("OrderDetails")
                .setRelationship(ASSOCIATION_ORDER_DETAIL_ORDER).setFromRole(ROLE_ORDER_DETAIL_ORDER_1).setToRole(
                        ROLE_ORDER_DETAIL_ORDER_2));

        //Key
        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_ORDER.getName())
                .setProperties(properties)
                .setKey(key)
                .setNavigationProperties(navigationProperties);
    }

    private EntityType buildCustomerEntity() {
        //Properties
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
                new Facets().setNullable(false)));
        properties.add(new SimpleProperty().setName("FirstName").setType(EdmSimpleTypeKind.String).setFacets(
                new Facets().setNullable(false).setMaxLength(100))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
        properties.add(new SimpleProperty().setName("LastName").setType(EdmSimpleTypeKind.String).setFacets(
                new Facets().setNullable(false).setMaxLength(100))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
        properties.add(new SimpleProperty().setName("UserName").setType(EdmSimpleTypeKind.String).setFacets(
                new Facets().setNullable(false).setMaxLength(100))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));

        //Key
        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_CUSTOMER.getName())
                .setProperties(properties)
                .setHasStream(true)
                .setKey(key);
    }

    private EntityType buildProductEntity() {
        //Properties
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
                new Facets().setNullable(false)));
        properties.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String).setFacets(
                new Facets().setNullable(false).setMaxLength(100))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
        properties.add(new SimpleProperty().setName("Description").setType(EdmSimpleTypeKind.String).setFacets(
                new Facets().setNullable(false).setMaxLength(100))
                .setCustomizableFeedMappings(
                        new CustomizableFeedMappings().setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE)));
        properties.add(new SimpleProperty().setName("Price").setType(EdmSimpleTypeKind.Decimal));
        properties.add(new SimpleProperty().setName("Weight").setType(EdmSimpleTypeKind.Decimal));

        //Key
        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Id"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_PRODUCT.getName())
                .setProperties(properties)
                .setKey(key);
    }

    private EntityType buildOrderDetailEntity() {
        //Properties
        List<Property> properties = new ArrayList<>();
        properties.add(new SimpleProperty().setName("Quantity").setType(EdmSimpleTypeKind.Int32));

        //Navigation Properties
        List<NavigationProperty> navigationProperties = new ArrayList<>();
        navigationProperties.add(new NavigationProperty().setName("Order")
                .setRelationship(ASSOCIATION_ORDER_DETAIL_ORDER).setFromRole(ROLE_ORDER_DETAIL_ORDER_1).setToRole(
                        ROLE_ORDER_DETAIL_ORDER_2));
        navigationProperties.add(new NavigationProperty().setName("Product")
                .setRelationship(ASSOCIATION_ORDER_DETAIL_PRODUCT).setFromRole(ROLE_ORDER_DETAIL_PRODUCT_1).setToRole(
                        ROLE_ORDER_DETAIL_PRODUCT_2));

        //Key
        List<PropertyRef> keyProperties = new ArrayList<>();
        keyProperties.add(new PropertyRef().setName("Order"));
        keyProperties.add(new PropertyRef().setName("Product"));
        Key key = new Key().setKeys(keyProperties);

        return new EntityType().setName(ENTITY_ORDER_DETAIL.getName())
                .setProperties(properties)
                .setKey(key)
                .setNavigationProperties(navigationProperties);
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
            if (ASSOCIATION_ORDER_CUSTOMER.getName().equals(edmFQName.getName())) {
                return new Association().setName(ASSOCIATION_ORDER_CUSTOMER.getName())
                        .setEnd1(new AssociationEnd().setType(ENTITY_ORDER).setRole(
                                ROLE_ORDER_CUSTOMER_1).setMultiplicity(EdmMultiplicity.MANY))
                        .setEnd2(new AssociationEnd().setType(ENTITY_CUSTOMER).setRole(
                                ROLE_ORDER_CUSTOMER_2).setMultiplicity(EdmMultiplicity.ONE));
            }

            if (ASSOCIATION_ORDER_DETAIL_ORDER.getName().equals(edmFQName.getName())) {
                return new Association().setName(ASSOCIATION_ORDER_DETAIL_ORDER.getName())
                        .setEnd1(new AssociationEnd().setType(ENTITY_ORDER).setRole(
                                ROLE_ORDER_DETAIL_ORDER_2).setMultiplicity(EdmMultiplicity.MANY))
                        .setEnd2(new AssociationEnd().setType(ENTITY_ORDER_DETAIL).setRole(
                                ROLE_ORDER_DETAIL_ORDER_1).setMultiplicity(EdmMultiplicity.ONE));
            }

            if (ASSOCIATION_ORDER_DETAIL_PRODUCT.getName().equals(edmFQName.getName())) {
                return new Association().setName(ASSOCIATION_ORDER_DETAIL_PRODUCT.getName())
                        .setEnd1(new AssociationEnd().setType(ENTITY_ORDER_DETAIL).setRole(
                                ROLE_ORDER_DETAIL_PRODUCT_1).setMultiplicity(EdmMultiplicity.ONE))
                        .setEnd2(new AssociationEnd().setType(ENTITY_PRODUCT).setRole(
                                ROLE_ORDER_DETAIL_PRODUCT_2).setMultiplicity(EdmMultiplicity.MANY));
            }
        }
        return null;
    }

    public EntitySet getEntitySet(String entityContainer, String name) throws ODataException {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            if (ENTITY_SET_NAME_ORDERS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_ORDER);
            }
            if (ENTITY_SET_NAME_CUSTOMERS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_CUSTOMER);
            }
            if (ENTITY_SET_NAME_PRODUCTS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_PRODUCT);
            }
            if (ENTITY_SET_NAME_ORDER_DETAILS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_ORDER_DETAIL);
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
        entityTypes.add(getEntityType(ENTITY_ORDER));
        entityTypes.add(getEntityType(ENTITY_CUSTOMER));
        entityTypes.add(getEntityType(ENTITY_PRODUCT));
        entityTypes.add(getEntityType(ENTITY_ORDER_DETAIL));
        schema.setEntityTypes(entityTypes);


        List<ComplexType> complexTypes = new ArrayList<>();
        complexTypes.add(getComplexType(COMPLEX_ADDRESS_TYPE));
        schema.setComplexTypes(complexTypes);


        List<Association> associations = new ArrayList<>();
        associations.add(getAssociation(ASSOCIATION_ORDER_CUSTOMER));
        associations.add(getAssociation(ASSOCIATION_ORDER_DETAIL_ORDER));
        associations.add(getAssociation(ASSOCIATION_ORDER_DETAIL_PRODUCT));
        schema.setAssociations(associations);

        List<EntityContainer> entityContainers = new ArrayList<>();
        EntityContainer entityContainer = new EntityContainer();
        entityContainer.setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);

        List<EntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_ORDERS));
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_CUSTOMERS));
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_PRODUCTS));
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_ORDER_DETAILS));
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
