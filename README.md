# Event-Driven Programming in Java — Week 1 Practical Activities

This repository contains the complete solutions, analysis, fixed source code, and working prototypes for **Week 1: Self-Paced Practical Activities for Event-Driven Programming**.

---

## Repository Structure

```text
event-driven-programming-java/
├── README.md                           # Main repository documentation & GitHub walkthrough
├── Activity1_ScavengerHunt.md          # Activity 1: 8 Real-world systems analysis & self-check
├── Activity2_EventLoopReflection.md    # Activity 2: Event Loop simulation answers & reflection
├── Week1_EDP/                          # Activity 3: Procedural vs. Event-Driven Side-by-Side
│   ├── procedural/
│   │   └── ProceduralCalculator.java   # Step-by-step console calculator
│   ├── eventdriven/
│   │   └── EventDrivenCalculator.java  # Interactive Swing event-driven calculator
│   └── Activity3_Comparison.md         # Answers, comparison matrix & paradigm reflection
├── BugFixChallenge/                    # Activity 4: Code Repair Challenge
│   ├── Bug1_BlockingEDT.java           # Fixed: Background thread prevents UI freezing
│   ├── Bug2_MissingListener.java       # Fixed: Added registered ActionListener
│   ├── Bug3_WrongThread.java           # Fixed: Updates dispatched to EDT via invokeLater
│   ├── Bug4_MemoryLeak.java            # Fixed: Collection references cleared on UI removal
│   └── BugFixes.md                     # Comprehensive debugging report & code changes
├── Activity5_SystemDesign/             # Activity 5: Design Your Own Event-Driven System
│   ├── SystemDesign.md                 # Design worksheet (Smart Home Controller IoT Hub)
│   └── SmartHomePrototype.java         # Interactive Java Swing prototype implementation
└── Activity6_SelfAssessment/           # Activity 6: Final Self-Assessment
    ├── SelfAssessment.md               # 15/15 questions answered across 4 learning outcomes
    └── Week1_Summary.md                # One-page executive summary & best practices
```

---

## Quick Compilation & Execution Guide

Ensure you have Java JDK (version 11 or higher) installed:

### 1. Compile All Modules
```bash
# Compile Activity 3 Calculators
javac Week1_EDP/procedural/ProceduralCalculator.java
javac Week1_EDP/eventdriven/EventDrivenCalculator.java

# Compile Activity 4 Bug Fixes
javac BugFixChallenge/*.java

# Compile Activity 5 Smart Home Prototype
javac Activity5_SystemDesign/SmartHomePrototype.java
```

### 2. Run Applications
```bash
# Run Procedural Calculator
java -cp Week1_EDP/procedural ProceduralCalculator

# Run Event-Driven Calculator
java -cp Week1_EDP/eventdriven EventDrivenCalculator

# Run Fixed Bug Challenge 1 (Non-blocking EDT)
java -cp BugFixChallenge Bug1_BlockingEDT

# Run Smart Home Controller Prototype
java -cp Activity5_SystemDesign SmartHomePrototype
```

---

## Complete GitHub Walkthrough (Completing Missing Steps 3 to 5)

### Step 3: Copy Files into Your Cloned Repository
If you cloned the repo via Step 2 into `event-driven-programming-java`, place all project directories and markdown files inside that folder.

Verify with:
```bash
git status
```
You will see untracked files listed in red.

### Step 4: Stage and Commit Your Changes
Stage all newly created files:
```bash
git add .
```

Commit the staged changes with a descriptive message:
```bash
git commit -m "Complete Week 1 Event-Driven Programming deliverables and activities"
```

### Step 5: Push Changes to GitHub
Push your committed changes to the `main` branch on GitHub:
```bash
git branch -M main
git push -u origin main
```

Navigate back to your repository page (`https://github.com/YOUR-USERNAME/event-driven-programming-java`) in your web browser. You will see all your files, code, and documentation displayed professionally!

---

## Deliverables Summary Matrix

| Activity | Topic / Task | Deliverable File | Status |
|---|---|---|---|
| **Activity 1** | Real-World EDP Scavenger Hunt | [Activity1_ScavengerHunt.md](Activity1_ScavengerHunt.md) | Completed |
| **Activity 2** | Event Loop Physical Simulation | [Activity2_EventLoopReflection.md](Activity2_EventLoopReflection.md) | Completed |
| **Activity 3** | Procedural vs Event-Driven Calculators | [ProceduralCalculator.java](Week1_EDP/procedural/ProceduralCalculator.java), [EventDrivenCalculator.java](Week1_EDP/eventdriven/EventDrivenCalculator.java), [Activity3_Comparison.md](Week1_EDP/Activity3_Comparison.md) | Completed |
| **Activity 4** | Debug the Event Handler Challenge | [Bug1_BlockingEDT.java](BugFixChallenge/Bug1_BlockingEDT.java), [Bug2_MissingListener.java](BugFixChallenge/Bug2_MissingListener.java), [Bug3_WrongThread.java](BugFixChallenge/Bug3_WrongThread.java), [Bug4_MemoryLeak.java](BugFixChallenge/Bug4_MemoryLeak.java), [BugFixes.md](BugFixChallenge/BugFixes.md) | Completed |
| **Activity 5** | Design Your Own Event-Driven System | [SystemDesign.md](Activity5_SystemDesign/SystemDesign.md), [SmartHomePrototype.java](Activity5_SystemDesign/SmartHomePrototype.java) | Completed |
| **Activity 6** | Self-Assessment & Executive Summary | [SelfAssessment.md](Activity6_SelfAssessment/SelfAssessment.md), [Week1_Summary.md](Activity6_SelfAssessment/Week1_Summary.md) | Completed |
