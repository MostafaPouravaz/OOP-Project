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

}
