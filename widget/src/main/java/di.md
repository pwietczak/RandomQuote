
### Root Cause
When Jetpack Glance manages app widgets (e.g., querying widget IDs or handling widget updates), it instantiates `GlanceAppWidgetReceiver` classes like `[QuoteWidgetReceiver](file:///C:/repo/RandomQuote/widget/src/main/java/eu/pw/widget/QuoteWidgetReceiver.kt)` via zero-argument reflection (`Class.newInstance()`) and directly accesses its `glanceAppWidget` property.

Because `QuoteWidgetReceiver` was instantiated directly via reflection rather than through a standard Android broadcast dispatch, `onReceive(context, intent)` was not invoked. Consequently, Hilt's `@AndroidEntryPoint` injection mechanism inside `onReceive` never ran, leaving `@Inject lateinit var quoteWidget: QuoteWidget` uninitialized and throwing `kotlin.UninitializedPropertyAccessException` when `glanceAppWidget` getter was invoked.

### Changes Made

1. **`[QuoteWidgetReceiver.kt](file:///C:/repo/RandomQuote/widget/src/main/java/eu/pw/widget/QuoteWidgetReceiver.kt)`**:
    - Removed `@AndroidEntryPoint` and the `@Inject lateinit var quoteWidget: QuoteWidget` property.
    - Initialized `glanceAppWidget` directly with a default-constructed `QuoteWidget()` instance.

2. **`[QuoteWidget.kt](file:///C:/repo/RandomQuote/widget/src/main/java/eu/pw/widget/presentation/QuoteWidget.kt)`**:
    - Changed `QuoteWidget` to have a no-argument constructor so it can be instantiated safely without dependencies at receiver creation time.
    - Added a Hilt `@EntryPoint` interface (`WidgetEntryPoint`) to lazily retrieve `QuoteDataStore` using `EntryPointAccessors.fromApplication` inside `provideGlance(context, id)`.

3. **`[QuoteWidgetUpdaterImpl.kt](file:///C:/repo/RandomQuote/widget/src/main/java/eu/pw/widget/QuoteWidgetUpdaterImpl.kt)`**:
    - Removed `private val widget: QuoteWidget` constructor parameter and invoked `QuoteWidget().updateAll(context)` directly.