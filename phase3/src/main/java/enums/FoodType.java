package enums;

public enum FoodType {
    FASTFOOD,
    IRANIANFOOD,
    SEAFOOD,
    APPETIZER,
    OTHER;


    public static FoodType getFoodTypeFromInt(int foodTypeID) {
        return switch (foodTypeID) {
            case 0 -> FASTFOOD;
            case 1 -> IRANIANFOOD;
            case 2 -> SEAFOOD;
            case 3 -> APPETIZER;
            case 4 -> OTHER;
            default -> null;
        };
    }
    public static int getIntFromFoodType(FoodType foodType) {
        return switch (foodType) {
            case FASTFOOD -> 0;
            case IRANIANFOOD -> 1;
            case SEAFOOD -> 2;
            case APPETIZER -> 3;
            case OTHER -> 4;
            default -> -1;
        };
    }
    public static String getNameFromFoodType(FoodType foodType) {
        return switch (foodType) {
            case FASTFOOD -> "Fast food";
            case IRANIANFOOD -> "Iranian food";
            case SEAFOOD -> "Sea food";
            case APPETIZER -> "Appetizer";
            case OTHER -> "Other";
            default -> null;
        };
    }
    public static String getFoodTypeNameWithFromInt(int foodTypeID) {
        return switch (foodTypeID) {
            case 0 -> "Fast food";
            case 1 -> "Iranian food";
            case 2 -> "Sea food";
            case 3 -> "Appetizer";
            case 4 -> "Other";
            default -> null;
        };
    }
    public static int getFoodTypeIntFromFoodTypeName(String foodType) {
        return switch (foodType) {
            case "Fast food" -> 0;
            case "Iranian food" -> 1;
            case "Sea food" -> 2;
            case "Appetizer" -> 3;
            case "Other" -> 4;
            default -> -1;
        };
    }

}
