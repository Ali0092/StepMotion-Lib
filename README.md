# StepMotion
[![](https://jitpack.io/v/Ali0092/StepMotion-Lib.svg)](https://jitpack.io/#Ali0092/StepMotion-Lib)


**StepMotion** is a lightweight, customizable, and elegant stepper UI library for Jetpack Compose. It helps you display multi-step progress in a clean and intuitive way — perfect for forms, onboarding flows, order tracking, and more.

---

## ✨ Features

- 🧽 Horizontal and Vertical Stepper with customizable steps and titles
- 🎨 Full color customization (active, inactive, title, indicator)
- ✅ Visually shows current, previous, and upcoming steps
- 🧹 Easy to integrate and use with clean APIs
- 💡 Ideal for progress tracking, sign-up forms, surveys, etc.

---

## 📸 Preview
<img width="30%" height="2400" alt="Screenshot_20260215_234011" src="https://github.com/user-attachments/assets/d269884e-5172-4019-a1d1-d09d5cf159d2" />
<img width="30%" height="2400" alt="Screenshot_20260215_234040" src="https://github.com/user-attachments/assets/7ce15525-de28-4aa9-82a5-d955f6b46f1a" />
<img width="30%" height="2400" alt="Screenshot_20260215_234100" src="https://github.com/user-attachments/assets/0ae5dd69-e1d3-4ad9-a83e-351ce285f979" />


---

## 🛠️ Installation

Add JitPack to your root `build.gradle`:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Then, add the dependency in your module's `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Ali0092:StepMotion-Lib:v1.0.4'
}
```

---

## How to Use

StepMotion provides **4 stepper variants** to fit different UI needs. All steppers use a **0-based** `currentStep` index and animate smoothly between state changes.

---

### 1. HorizontalSimpleStepper

A clean horizontal stepper with circle indicators and text labels below each step.

<!-- Screenshot placeholder -->

```kotlin
HorizontalSimpleStepper(
    steps = listOf("Profile", "Upload", "Review", "Done"),
    currentStep = 2,
    activeColor = Color(0xFF3F51B5),
    inactiveColor = Color.LightGray,
    activeTitleColor = Color.Black,
    inactiveTitleColor = Color.Gray
)
```

**Optional parameters:**

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `16.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `13.sp` | Font size for step labels |
| `spacing` | `Dp` | `4.dp` | Vertical spacing between circle and label |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `borderWidth` | `Dp` | `2.dp` | Border width around the current step circle |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 2. HorizontalAnimatedStepper

A horizontal stepper with a pulse ring animation on the current step for added visual emphasis.

<!-- Screenshot placeholder -->

```kotlin
HorizontalAnimatedStepper(
    steps = listOf("Profile", "Upload", "Review", "Done"),
    currentStep = 1,
    activeColor = Color(0xFF3F51B5),
    inactiveColor = Color.LightGray,
    activeTitleColor = Color.Black,
    inactiveTitleColor = Color.Gray
)
```

**Optional parameters:**

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `16.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `12.sp` | Font size for step labels |
| `spacing` | `Dp` | `6.dp` | Vertical spacing between circle and label |
| `connectorThickness` | `Dp` | `3.dp` | Thickness of connector lines |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |
| `enablePulseAnimation` | `Boolean` | `true` | Enable/disable pulse ring on current step |
| `pulseAnimationDuration` | `Int` | `1000` | Duration of the pulse animation cycle |

---

### 3. VerticalSimpleStepper

A vertical stepper with circles on the left and text labels on the right, connected by vertical lines.

<!-- Screenshot placeholder -->

```kotlin
VerticalSimpleStepper(
    steps = listOf("Profile", "Upload", "Review", "Done"),
    currentStep = 2,
    activeColor = Color(0xFF3F51B5),
    inactiveColor = Color.LightGray,
    activeTitleColor = Color.Black,
    inactiveTitleColor = Color.Gray
)
```

**Optional parameters:**

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `16.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `16.sp` | Font size for step labels |
| `horizontalSpacing` | `Dp` | `16.dp` | Spacing between circles and text labels |
| `verticalSpacing` | `Dp` | `4.dp` | Spacing around connector lines |
| `connectorThickness` | `Dp` | `2.dp` | Thickness of connector lines |
| `borderWidth` | `Dp` | `2.dp` | Border width around the current step circle |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### 4. VerticalCardStepper

A vertical stepper with expandable cards that show descriptions for the current step. Great for onboarding flows and order tracking.

<!-- Screenshot placeholder -->

```kotlin
VerticalCardStepper(
    steps = listOf("Profile", "Upload", "Review", "Done"),
    descriptions = listOf(
        "Fill in your personal details",
        "Upload required documents",
        "Review your application",
        "You're all set!"
    ),
    currentStep = 1,
    activeColor = Color(0xFF3F51B5),
    inactiveColor = Color.LightGray,
    cardBackgroundColor = Color(0xFFF5F5F5),
    activeCardBackgroundColor = Color(0xFFE8EAF6)
)
```

**Optional parameters:**

| Parameter | Type | Default | Description |
|---|---|---|---|
| `circleSize` | `Dp` | `32.dp` | Size of step indicator circles |
| `circleFontSize` | `TextUnit` | `15.sp` | Font size for numbers inside circles |
| `titleFontSize` | `TextUnit` | `15.sp` | Font size for step titles |
| `descriptionFontSize` | `TextUnit` | `12.sp` | Font size for step descriptions |
| `cardRadius` | `Dp` | `12.dp` | Corner radius for step cards |
| `cardPadding` | `Dp` | `14.dp` | Padding inside step cards |
| `cardSpacing` | `Dp` | `8.dp` | Spacing between step cards |
| `horizontalSpacing` | `Dp` | `12.dp` | Spacing between circles and cards |
| `connectorThickness` | `Dp` | `3.dp` | Thickness of connector lines |
| `accentBorderWidth` | `Dp` | `4.dp` | Width of the colored accent border on cards |
| `animationDuration` | `Int` | `400` | Animation duration in milliseconds |

---

### Common Parameters (all steppers)

| Parameter | Type | Description |
|---|---|---|
| `steps` | `List<String>` | List of step labels to display |
| `currentStep` | `Int` | Current step index (0-based) |
| `activeColor` | `Color` | Color for completed and current step indicators |
| `inactiveColor` | `Color` | Color for future/upcoming step indicators |
| `activeTitleColor` | `Color` | Text color for completed and current steps |
| `inactiveTitleColor` | `Color` | Text color for future steps |
| `modifier` | `Modifier` | Modifier to apply to the stepper |

---

## 🤝 Contribution

Feel free to fork, star, or suggest improvements via issues and pull requests.

---

## 📄 License

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

## 👤 Author

**Muhammad Ali**\
📧 [aliatwork364@gmail.com](mailto\:aliatwork364@gmail.com)\
🌐 [Portfolio](https://muhammadali0092.netlify.app)\
💼 [LinkedIn](https://www.linkedin.com/in/muhammad-ali-a28422222)\
💻 [GitHub](https://github.com/Ali0092)

---

> *“Step into motion — guide your users one smooth step at a time.”*

