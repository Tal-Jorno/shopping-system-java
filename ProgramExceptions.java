package TalJorno_MayShabat;

public class ProgramExceptions extends Exception {
    public ProgramExceptions(String message) {
        super(message);
    }

    public static class EmptyInputException extends ProgramExceptions {
        public EmptyInputException(String message) {
            super(message);
        }
    }

    public static class NegativeNumber extends ProgramExceptions {
        public NegativeNumber(String message) {
            super(message);
        }
    }

    public static class NotFoundObject extends ProgramExceptions {
        public NotFoundObject(String message) {
            super(message);
        }
    }

    public static class DuplicateNames extends ProgramExceptions {
        public DuplicateNames(String message) {
            super(message);
        }
    }

    public static class notInitializedStructure extends ProgramExceptions {
        public notInitializedStructure(String message) {
            super(message);
        }
    }


}
