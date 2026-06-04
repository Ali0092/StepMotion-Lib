# StepMotion
[![](https://jitpack.io/v/Ali0092/StepMotion-Lib.svg)](https://jitpack.io/#Ali0092/StepMotion-Lib)

**StepMotion** is a lightweight, customizable, and animated stepper UI library for Jetpack Compose. Display multi-step progress in a clean and intuitive way — perfect for forms, onboarding flows, order tracking, checkout flows, and more.

---

## Features

- 12 stepper variants — 6 horizontal + 6 vertical
- Full color customization (active, inactive, title, indicator)
- Smooth animations for state transitions (color, scale, checkmark, connector fill)
- Visually shows current, previous, and upcoming steps
- Clean, minimal APIs — drop in and go
- Ideal for progress tracking, sign-up flows, surveys, checkout, onboarding, and more

---

## Preview

<img width="30%" height="2400" alt="Screenshot_20260215_234011" src="https://github.com/user-attachments/assets/d269884e-5172-4019-a1d1-d09d5cf159d2" />
<img width="30%" height="2400" alt="Screenshot_20260215_234040" src="https://github.com/user-attachments/assets/7ce15525-de28-4aa9-82a5-d955f6b46f1a" />
<img width="30%" height="2400" alt="Screenshot_20260215_234100" src="https://github.com/user-attachments/assets/0ae5dd69-e1d3-4ad9-a83e-351ce285f979" />

<img width="30%" height="2400" alt="Screenshot 2026-06-04 at 3 09 37 PM" src="https://github.com/user-attachments/assets/235cd7e5-ea06-4a79-a7b8-fce1fcfcad61" />
<img width="30%" height="2400" alt="Screenshot 2026-06-04 at 3 10 47 PM" src="https://github.com/user-attachments/assets/b718385c-8a02-42d8-8fa3-df9572e77e61" />

<img width="30%" height="2400" alt="Screenshot 2026-06-04 at 3 11 05 PM" src="https://github.com/user-attachments/assets/fd7aa447-7cf6-461a-95cc-7e9aaad33426" />

<img width="30%" height="2400" alt="Screenshot 2026-06-04 at 3 11 35 PM" src="https://github.com/user-attachments/assets/b70e47ee-faf5-49e7-b4be-1ef57144a44c" />

---

## Installation

Add JitPack to your root `settings.gradle` (or `build.gradle`):

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency in your module's `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Ali0092:StepMotion-Lib:v1.0.4'
}
```

---

## Stepper Types

All steppers share a common set of required parameters and expose optional parameters for fine-tuning. Steps use a **0-based** `currentStep` index and animate smoothly between state changes.

### Common Parameters

| Parameter | Type | Description |
|---|---|---|
| `steps` | `List<String>` | Step labels to display |
| `currentStep` | `Int` | Current step index (0-based) |
| `activeColor` | `Color` | Color for completed and current step indicators |
| `inactiveColor` | `Color` | Color for upcoming step indicators |
| `activeTitleColor` | `Color` | Text color for completed and current steps |
| `inactiveTitleColor` | `Color` | Text color for upcoming steps |
| `modifier` | `Modifier` | Modifier to apply to the stepper |

---

### 1. Simple Stepper

A clean stepper with filled circle indicators and animated connectors.

#### HorizontalStepper

```kotlin
HorizontalStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Details", "Address", "Payment", "Done"),
    currentStep = 1,
    activeColor = Color(0xFF3B82F6),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF1E40AF),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `14.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `13.sp` | Font size for step labels |
| `spacing` | `Dp` | `4.dp` | Gap between circle and label |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `borderWidth` | `Dp` | `2.dp` | Border width around the current step circle |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

#### VerticalSimpleStepper

```kotlin
VerticalSimpleStepper(
    modifier = Modifier.fillMaxWidth().height(280.dp),
    steps = listOf("Select", "Confirm", "Pay", "Complete"),
    currentStep = 1,
    activeColor = Color(0xFFF59E0B),
    inactiveColor = Color(0xFFE2E8F0),
    activeTitleColor = Color(0xFF92400E),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `14.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `14.sp` | Font size for step labels |
| `horizontalSpacing` | `Dp` | `16.dp` | Gap between circles and text labels |
| `verticalSpacing` | `Dp` | `4.dp` | Spacing around connector lines |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `borderWidth` | `Dp` | `2.dp` | Border width around the current step circle |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 2. Animated Stepper

A horizontal stepper with a repeating pulse ring on the current step for extra visual emphasis.

#### HorizontalAnimatedStepper

```kotlin
HorizontalAnimatedStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Cart", "Shipping", "Payment", "Confirm"),
    currentStep = 1,
    activeColor = Color(0xFF10B981),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF065F46),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `14.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `12.sp` | Font size for step labels |
| `spacing` | `Dp` | `6.dp` | Gap between circle and label |
| `connectorThickness` | `Dp` | `3.dp` | Thickness of connector lines |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |
| `enablePulseAnimation` | `Boolean` | `true` | Toggle the pulse ring on current step |
| `pulseAnimationDuration` | `Int` | `1000` | Duration of one pulse cycle in milliseconds |

---

### 3. Numbered Stepper

Outlined circles for inactive steps, filled + outer glow ring for the current step, checkmark for completed steps.

#### HorizontalNumberedStepper

```kotlin
HorizontalNumberedStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Details", "Review", "Payment", "Confirm"),
    currentStep = 1,
    activeColor = Color(0xFF7C3AED),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF4C1D95),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

#### VerticalNumberedStepper

```kotlin
VerticalNumberedStepper(
    modifier = Modifier.fillMaxWidth().height(240.dp),
    steps = listOf("Setup", "Configure", "Preview", "Launch"),
    currentStep = 1,
    activeColor = Color(0xFF0EA5E9),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF0C4A6E),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `14.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `13.sp` | Font size for step labels |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `outerRingWidth` | `Dp` | `2.dp` | Width of the outer glow ring on the current step |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 4. Icon Stepper

Per-step custom icons — outlined and tinted when inactive, white on filled background when active, checkmark when completed.

#### HorizontalIconStepper

```kotlin
HorizontalIconStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Profile", "Home", "Cart", "Done"),
    icons = listOf(
        Icons.Rounded.Person,
        Icons.Rounded.Home,
        Icons.Rounded.ShoppingCart,
        Icons.Rounded.Star
    ),
    currentStep = 1,
    activeColor = Color(0xFFF59E0B),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF78350F),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

#### VerticalIconStepper

```kotlin
VerticalIconStepper(
    modifier = Modifier.fillMaxWidth().height(240.dp),
    steps = listOf("Account", "Address", "Wishlist", "Favourites"),
    icons = listOf(
        Icons.Rounded.Person,
        Icons.Rounded.Home,
        Icons.Rounded.Star,
        Icons.Rounded.Favorite
    ),
    currentStep = 1,
    activeColor = Color(0xFFEC4899),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF831843),
    inactiveTitleColor = Color(0xFF94A3B8)
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `icons` | `List<ImageVector>` | — | Custom icon for each step (must match `steps` length) |
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `iconSize` | `Dp` | `18.dp` | Size of the icon drawn inside the circle |
| `titleFontSize` | `TextUnit` | `13.sp` | Font size for step labels |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 5. Rounded Stepper

Rounded-rectangle badge indicators instead of circles — configurable corner radius, not fully pill-shaped.

#### HorizontalRoundedStepper

```kotlin
HorizontalRoundedStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Symptoms", "History", "Rx", "Done"),
    currentStep = 1,
    activeColor = Color(0xFF10B981),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF064E3B),
    inactiveTitleColor = Color(0xFF94A3B8),
    cornerRadius = 10.dp
)
```

#### VerticalRoundedStepper

```kotlin
VerticalRoundedStepper(
    modifier = Modifier.fillMaxWidth().height(260.dp),
    steps = listOf("Intake", "Diagnosis", "Prescription", "Follow-up"),
    currentStep = 1,
    activeColor = Color(0xFF6366F1),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF312E81),
    inactiveTitleColor = Color(0xFF94A3B8),
    cornerRadius = 10.dp
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `badgeWidth` | `Dp` | `44.dp` | Width of each badge indicator |
| `badgeHeight` | `Dp` | `32.dp` | Height of each badge indicator |
| `cornerRadius` | `Dp` | `10.dp` | Corner radius of the badge (0 = rectangle, high value = pill) |
| `circleFontSize` | `TextUnit` | `14.sp` | Font size for numbers inside badges |
| `titleFontSize` | `TextUnit` | `13.sp` | Font size for step labels |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 6. Progress Bar Stepper

A thick continuous bar with step dots on top — dots fill from left to right with half-fill on the current step.

#### HorizontalProgressBarStepper

```kotlin
HorizontalProgressBarStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Start", "Design", "Build", "Test", "Ship"),
    currentStep = 2,
    activeColor = Color(0xFFEF4444),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF7F1D1D),
    inactiveTitleColor = Color(0xFF94A3B8),
    barThickness = 6.dp
)
```

#### VerticalProgressBarStepper

```kotlin
VerticalProgressBarStepper(
    modifier = Modifier.fillMaxWidth().height(300.dp),
    steps = listOf("Order placed", "Processing", "Shipped", "Out for delivery", "Delivered"),
    currentStep = 2,
    activeColor = Color(0xFF14B8A6),
    inactiveColor = Color(0xFFCBD5E1),
    activeTitleColor = Color(0xFF134E4A),
    inactiveTitleColor = Color(0xFF94A3B8),
    barThickness = 6.dp
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `dotSize` | `Dp` | `16.dp` | Diameter of each step dot |
| `barThickness` | `Dp` | `4.dp` | Thickness of the continuous progress bar |
| `titleFontSize` | `TextUnit` | `13.sp` | Font size for step labels |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 7. Card Stepper

A vertical stepper with expandable cards showing a description for each step. Great for onboarding and order tracking.

#### VerticalCardStepper

```kotlin
VerticalCardStepper(
    modifier = Modifier.fillMaxWidth(),
    steps = listOf("Create Account", "Personal Info", "Preferences", "Verification", "All Set!"),
    descriptions = listOf(
        "Sign up with your email and create a password",
        "Tell us your name and contact details",
        "Choose your notification and display preferences",
        "Verify your email address to continue",
        "Your account is ready to use"
    ),
    currentStep = 1,
    activeColor = Color(0xFF8B5CF6),
    inactiveColor = Color(0xFF94A3B8),
    activeCardBackgroundColor = Color(0xFFF5F3FF),
    cardBackgroundColor = Color(0xFFFFFFFF)
)
```

| Parameter | Type | Default | Description |
|---|---|---|---|
| `descriptions` | `List<String>` | — | Description shown inside each card |
| `activeCardBackgroundColor` | `Color` | — | Background color of the active card |
| `cardBackgroundColor` | `Color` | — | Background color of inactive cards |
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `15.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `15.sp` | Font size for step titles |
| `descriptionFontSize` | `TextUnit` | `12.sp` | Font size for step descriptions |
| `cardRadius` | `Dp` | `12.dp` | Corner radius for step cards |
| `cardPadding` | `Dp` | `14.dp` | Padding inside step cards |
| `cardSpacing` | `Dp` | `8.dp` | Spacing between step cards |
| `horizontalSpacing` | `Dp` | `12.dp` | Gap between circles and cards |
| `connectorThickness` | `Dp` | `3.dp` | Thickness of connector lines |
| `accentBorderWidth` | `Dp` | `4.dp` | Width of the accent border on the active card |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

## Contribution

Feel free to fork, star, or suggest improvements via issues and pull requests.

---

## License

```
MIT License

Copyright (c) 2025 Muhammad Ali

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## Author

**Muhammad Ali**\
📧 [aliatwork364@gmail.com](mailto:aliatwork364@gmail.com)\
🌐 [Portfolio](https://muhammadali0092.netlify.app)\
💼 [LinkedIn](https://www.linkedin.com/in/muhammad-ali-a28422222)\
💻 [GitHub](https://github.com/Ali0092)

---

> *"Step into motion — guide your users one smooth step at a time."*
