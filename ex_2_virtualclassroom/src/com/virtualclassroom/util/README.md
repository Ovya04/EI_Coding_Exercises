# Utility Classes

Holds support classes that provide general-purpose utilities for use by the manager, entities, or other project components.

**Files:**
- `Logger.java`: Implements singleton-style logging for audit, debugging, and error messages.
- `ValidationHelper.java`: Offers methods for checking and sanitizing inputs, masking emails, and other validation tasks.

**Why?**
- Keeps reusable, generic logic out of main code.
- Simplifies code in other classes by providing helper functions.
