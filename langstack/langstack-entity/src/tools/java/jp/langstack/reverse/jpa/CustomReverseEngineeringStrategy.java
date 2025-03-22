package jp.langstack.reverse.jpa;

import java.util.List;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.mapping.ForeignKey;

public class CustomReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

    private static final String CLASS_SUFFIX_MULTIPLE = "Entities";
    private static final String CLASS_SUFFIX = "Entity";
    private static final String CORRECTION_COLLECTION_NAME_REGEX_ADD_ES = ".*(s|sh|ch|o|x)$";
    private static final String CORRECTION_COLLECTION_NAME_REGEX_REPLACE_IES = ".*[^aiueo]y$";
    private static final String CORRECTION_COLLECTION_NAME_REGEX_REPLACE_VES = ".*fe?$";
  

    public CustomReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
        super(delegate);
    }

    // 生成されるエンティティのクラス名末尾に「Entity」を追加
    @Override
    public String tableToClassName(TableIdentifier tableIdentifier) {
        return super.tableToClassName(tableIdentifier) + CLASS_SUFFIX;
    }

    // 【1 対 1】
    @Override
    public String foreignKeyToInverseEntityName(
        String keyname,
        TableIdentifier fromTable,
        List<?> fromColumnNames,
        TableIdentifier referencedTable,
        List<?> referencedColumnNames,
        boolean uniqueReference) {
      return super.foreignKeyToInverseEntityName(
              keyname,
              fromTable,
              fromColumnNames,
              referencedTable,
              referencedColumnNames,
              uniqueReference)
          .replace(CLASS_SUFFIX, "");
    }

    // 【1 対 多？】の1側？
    // CardEntity内で
    // ✕ 「GenreEntity genreEntity」
    // 〇 「GenreEntity genre」
    // となるように設定するところ
    @Override
    public String foreignKeyToEntityName(
        String keyname,
        TableIdentifier fromTable,
        List<?> fromColumnNames,
        TableIdentifier referencedTable,
        List<?> referencedColumnNames,
        boolean uniqueReference) {
      return super.foreignKeyToEntityName(
              keyname,
              fromTable,
              fromColumnNames,
              referencedTable,
              referencedColumnNames,
              uniqueReference)
          .replace(CLASS_SUFFIX, "");
    }

    // GenreEntity内で
    // ✕ 「List<CardEntity> cardEntitties」
    // 〇 「List<CardEntity> cards」
    // となるように設定するところ？
    @Override
    public String foreignKeyToCollectionName(
        String keyname,
        TableIdentifier fromTable,
        List<?> fromColumns,
        TableIdentifier referencedTable,
        List<?> referencedColumns,
        boolean uniqueReference) {
      return correctPluralName(
          super.foreignKeyToCollectionName(
              keyname, fromTable, fromColumns, referencedTable, referencedColumns, uniqueReference));
    }


    // 【多 対 多】
    @Override
    public String foreignKeyToManyToManyName(
        ForeignKey fromKey, TableIdentifier middleTable, ForeignKey toKey, boolean uniqueReference) {
      return correctPluralName(
          super.foreignKeyToManyToManyName(fromKey, middleTable, toKey, uniqueReference));
    }

    static String correctPluralName(String defaultName) {
        String correctionName = defaultName.replace(CLASS_SUFFIX_MULTIPLE, "");
        if (correctionName.matches(CORRECTION_COLLECTION_NAME_REGEX_ADD_ES)) {
          return correctionName + "es";
        }
        if (correctionName.matches(CORRECTION_COLLECTION_NAME_REGEX_REPLACE_IES)) {
          return correctionName.replaceAll("y$", "ies");
        }
        if (correctionName.matches(CORRECTION_COLLECTION_NAME_REGEX_REPLACE_VES)) {
          return correctionName.replaceAll("fe?$", "ves");
        }
        return correctionName + "s";
      }


}