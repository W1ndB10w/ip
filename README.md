
# Reverie: User Guide

Reverie is a personal task manager chatbot that helps you track todos, deadlines, and events via a simple command-line interface.

---

## Table of Contents

- [Quick Start](#quick-start)
- [Features](#features)
    - [Understanding Commands](#understanding-commands)
    - [Adding Tasks: `todo`, `deadline`, `event`](#adding-tasks-todo-deadline-event)
    - [Date & Time Formats](#date--time-formats)
    - [Viewing Tasks: `list`, `find`, `schedule`](#viewing-tasks-list-find-schedule)
    - [Managing Tasks: `mark`, `unmark`, `delete`](#managing-tasks-mark-unmark-delete)
    - [Exit: `bye`](#exit-bye)
- [Task Symbols](#task-symbols)
- [Data Storage](#data-storage)
- [Command Summary](#command-summary)
- [Tips](#tips)
- [Appendix: Complete List of Accepted Date/Time Formats](#appendix-complete-list-of-accepted-datetime-formats)

---

## Quick Start

1. Ensure you have Java 17 installed
2. Download `reverie.jar`
3. Open terminal, navigate to the jar file location
4. Run: `java -jar reverie.jar`
5. Type commands and press Enter

---

## Features

### Understanding Commands

* Words in `UPPER_CASE` are parameters you need to provide
    * Example: `todo DESCRIPTION` → `todo read book`
* Items in `[square brackets]` are optional
    * Example: `deadline TASK /by DATE [TIME]` can be used with or without time
* **All command words are case-insensitive**
    * Example: The command word `todo` can be replaced by `TODO`, `ToDo`, `toDo`, etc.
* Task numbers start from 1 (shown when you use `list`)

---

### Adding Tasks: `todo`, `deadline`, `event`

#### Todo: `todo`
Simple tasks without dates.

**Format:** `todo DESCRIPTION`  
**Case-sensitivity:** Command word `todo` is case-insensitive.

```
todo read book
```
```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task in the list.
```

---

#### Deadline: `deadline`
Tasks with a due date/time.

**Format:** `deadline DESCRIPTION /by DATE_TIME`  
**Case-sensitivity:** Command word `deadline` is case-insensitive. Keyword `/by` is **case-sensitive (lowercase only)**.

```
deadline submit report /by 2019-12-02
```
```
Got it. I've added this task:
  [D][ ] submit report (by: Dec 02 2019)
Now you have 2 tasks in the list.
```

```
deadline return book /by Dec 02 2019 1800
```
```
Got it. I've added this task:
  [D][ ] return book (by: 18:00 Dec 02 2019)
Now you have 3 tasks in the list.
```

⚠️ **Important:** If the text after `/by` doesn't match any accepted date/time format, Reverie will treat it as plain text, not as a date. Always check the output to verify your date was recognized.

---

#### Event: `event`
Tasks with start and end times.

**Format:** `event DESCRIPTION /from START /to END`  
**Case-sensitivity:** Command word `event` is case-insensitive. Keywords `/from`, `/to` are **case-sensitive (lowercase only)**.

```
event meeting /from 2019-12-02 1400 /to 2019-12-02 1600
```
```
Got it. I've added this task:
  [E][ ] meeting (from: 14:00 Dec 02 2019 to: 16:00 Dec 02 2019)
Now you have 4 tasks in the list.
```

```
event lunch /from 1200 /to 1400
```
```
Got it. I've added this task:
  [E][ ] lunch (from: 12:00 Oct 05 2024 to: 14:00 Oct 05 2024)
Now you have 5 tasks in the list.
```

💡 **Tips:**
* **Smart date inference:**
    * If the start has a date but the end has only time (e.g., `/from Dec 02 2019 0900 /to 1700`), Reverie intelligently decides and sets the **end date = start date (or start date + 1 if end time < start time)**.
    * If the end has a date but the start has only time (e.g., `/from 0900 /to Dec 02 2019 1700`), Reverie intelligently decides and sets the **start date = end date (or end date - 1 if start time > end time)**.
* If you provide only time (e.g., `1200`), today's date is used automatically; if both are time-only (e.g., `/from 1200 /to 1400`), today's date is used for both.

⚠️ **Important:** If the text after `/from` or `/to` doesn't match any accepted date/time format, Reverie will treat it as plain text, not as a date/time. Always check the output to verify your dates were recognized.

---

### Date & Time Formats

Reverie accepts specific date and time formats:
* Month names: **capitalize only the first letter** (e.g., `Apr`, `Sep`, `Oct`, `Dec`).
* AM/PM indicators are case-insensitive.

#### Format Rules

**Date formats:**
- Separators: `-` or `/`
- Date orders: `yyyy-MM-dd`, `dd-MM-yyyy`, `MMM dd yyyy`, `dd MMM yyyy`, `yyyy MMM dd`
- Examples: `2019-12-02`, `02/12/2019`, `Dec 02 2019`

**Time formats:**
- 24-hour: `HHmm` or `HH:mm`
    - Examples: `1400`, `14:00`
- 12-hour: `h:mma`, `h:mm a`, `hh:mma`, `hh:mm a`, `hmma`, `hmm a`, `hhmma`, `hhmm a`
    - **12-hour formats are ONLY valid when time is provided alone, NOT in date-time combinations**
    - Examples: `2:00pm`, `2:00 pm`, `02:00pm`, `02:00 pm`

**Date-time combinations:**
- Date + Time: `2019-12-02 1400`, `Dec 02 2019 14:00`, `02/12/2019 1400`
- Time + Date: `1400 2019-12-02`, `14:00 Dec 02 2019`, `1400 02/12/2019`
- ISO format: `2019-12-02T14:00`, `2019-12-02T14:00:00`

**Quick examples:**
* `deadline report /by 2019-12-02`
* `deadline report /by Dec 02 2019 1400`
* `deadline report /by 02/12/2019 2:00pm`
* `deadline report /by 1400 2019-12-02`
* `deadline report /by 2:00pm`
* `event meeting /from 1400 /to 1600`
* `event meeting /from Dec 02 2019 /to Dec 04 2019`

📘 **For the complete list of accepted date/time formats, please refer to the [Appendix](#appendix-complete-list-of-accepted-datetime-formats).**

⚠️ **Important:**
* Only formats listed above are recognized as dates/times
* If you use a different format (e.g., `December 2, 2019` or `2nd Dec 2019`), Reverie will NOT recognize it as a date and will treat it as plain text
* Always verify the output shows your intended date/time in the confirmation message

---

### Viewing Tasks: `list`, `find`, `schedule`

#### List all tasks: `list`

**Format:** `list`  
**Case-sensitivity:** Command word `list` is case-insensitive.

```
list
```
```
Here are the tasks in your list:
 1.[T][ ] read book
 2.[D][ ] submit report (by: Dec 02 2019)
 3.[E][ ] meeting (from: 14:00 Dec 02 2019 to: 16:00 Dec 02 2019)
```

---

#### Find tasks: `find`
Search by keyword (case-insensitive match).

**Format:** `find KEYWORD`  
**Case-sensitivity:** Command word `find` is case-insensitive. **Keyword matching is case-insensitive.**

```
find book
```
```
Here are the matching tasks in your list:
 1.[T][ ] read book
 5.[D][ ] return library book (by: Dec 10 2019)
```

```
find BOOK
```
```
Here are the matching tasks in your list:
 1.[T][ ] read book
 5.[D][ ] return library book (by: Dec 10 2019)
```

---

#### View schedule: `schedule`
See tasks on a specific date. **Date must be in `yyyy-MM-dd` format only.**

**Format:** `schedule DATE`  
**Case-sensitivity:** Command word `schedule` is case-insensitive.

```
schedule 2019-12-02
```
```
Here are the tasks scheduled for Dec 02 2019:
 2.[D][ ] submit report (by: Dec 02 2019)
 3.[E][ ] meeting (from: 14:00 Dec 02 2019 to: 16:00 Dec 02 2019)
```

⚠️ **Important:** For the `schedule` command, only `yyyy-MM-dd` format is accepted (e.g., `2019-12-02`). Other formats will cause an error.

---

### Managing Tasks: `mark`, `unmark`, `delete`

#### Mark as done: `mark`

**Format:** `mark TASK_NUMBER`  
**Case-sensitivity:** Command word `mark` is case-insensitive.

```
mark 1
```
```
Nice! I've marked this task as done:
  [T][X] read book
```

---

#### Mark as not done: `unmark`

**Format:** `unmark TASK_NUMBER`  
**Case-sensitivity:** Command word `unmark` is case-insensitive.

```
unmark 1
```
```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

---

#### Delete task: `delete`

**Format:** `delete TASK_NUMBER`  
**Case-sensitivity:** Command word `delete` is case-insensitive.

```
delete 1
```
```
Noted. I've removed this task:
  [T][ ] read book
Now you have 4 tasks in the list.
```

⚠️ **Important:** Deletion is permanent. Task numbers shift after deletion - use `list` to verify.

---

### Exit: `bye`

**Format:** `bye`  
**Case-sensitivity:** Command word `bye` is case-insensitive.

```
bye
```
```
Bye. Hope to see you again soon!
```

---

## Task Symbols

* `[T]` = Todo | `[D]` = Deadline | `[E]` = Event
* `[ ]` = Not done | `[X]` = Done

**Examples:**
* `[T][ ] read book` - Todo, not done
* `[D][X] submit report (by: Dec 02 2019)` - Deadline, completed
* `[E][ ] meeting (from: 14:00 Dec 02 2019 to: 16:00 Dec 02 2019)` - Event, not done

---

## Data Storage

* Tasks auto-save to `./data/reverie.txt` after every change
* Autoloads on startup
* ⚠️ **Do not manually edit** the data file - may cause corruption

**To transfer data:** Copy the entire `data` folder to your new computer.

---

## Command Summary

| Command         | Format (case-sensitive only for `/by`, `/from`, `/to`) | Example                             |
|-----------------|--------------------------------------------------------|-------------------------------------|
| Add `todo`      | `todo DESCRIPTION`                                     | `todo read book`                    |
| Add `deadline`  | `deadline DESCRIPTION /by DATE_TIME`                   | `deadline report /by Dec 02 2019`   |
| Add `event`     | `event DESCRIPTION /from START /to END`                | `event meeting /from 1400 /to 1600` |
| `List` tasks    | `list`                                                 | `list`                              |
| `Find` tasks    | `find KEYWORD`                                         | `find book`                         |
| View `schedule` | `schedule DATE`                                        | `schedule 2019-12-02`               |
| `Mark` done     | `mark TASK_NUMBER`                                     | `mark 1`                            |
| `Unmark`        | `unmark TASK_NUMBER`                                   | `unmark 1`                          |
| `Delete`        | `delete TASK_NUMBER`                                   | `delete 1`                          |
| Exit with `bye` | `bye`                                                  | `bye`                               |

---

## Tips

✅ Use descriptive task names for clarity  
✅ Check `list` before using task numbers  
✅ Use `schedule` to plan your day  
✅ Back up your `data` folder regularly  
✅ Time-only entries (`1400`) default to today  
✅ **Always verify the output message** to confirm dates/times were recognized correctly

---

## Appendix: Complete List of Accepted Date/Time Formats

### Date-Time Formats (with both date and time)

| Format Pattern          | Example             |
|-------------------------|---------------------|
| `yyyy-MM-dd HHmm`       | 2025-10-05 1430     |
| `yyyy-MM-dd HH:mm`      | 2025-10-05 14:30    |
| `yyyy/MM/dd HHmm`       | 2025/10/05 1430     |
| `yyyy/MM/dd HH:mm`      | 2025/10/05 14:30    |
| `dd-MM-yyyy HHmm`       | 05-10-2025 1430     |
| `dd-MM-yyyy HH:mm`      | 05-10-2025 14:30    |
| `dd/MM/yyyy HHmm`       | 05/10/2025 1430     |
| `dd/MM/yyyy HH:mm`      | 05/10/2025 14:30    |
| `yyyy-MM-dd'T'HH:mm`    | 2025-10-05T14:30    |
| `yyyy-MM-dd'T'HH:mm:ss` | 2025-10-05T14:30:45 |
| `yyyy MMM dd HHmm`      | 2025 Oct 05 1430    |
| `yyyy MMM dd HH:mm`     | 2025 Oct 05 14:30   |
| `MMM dd yyyy HHmm`      | Oct 05 2025 1430    |
| `MMM dd yyyy HH:mm`     | Oct 05 2025 14:30   |
| `dd MMM yyyy HHmm`      | 05 Oct 2025 1430    |
| `dd MMM yyyy HH:mm`     | 05 Oct 2025 14:30   |
| `HHmm yyyy-MM-dd`       | 1430 2025-10-05     |
| `HH:mm yyyy-MM-dd`      | 14:30 2025-10-05    |
| `HHmm yyyy/MM/dd`       | 1430 2025/10/05     |
| `HH:mm yyyy/MM/dd`      | 14:30 2025/10/05    |
| `HHmm dd-MM-yyyy`       | 1430 05-10-2025     |
| `HH:mm dd-MM-yyyy`      | 14:30 05-10-2025    |
| `HHmm dd/MM/yyyy`       | 1430 05/10/2025     |
| `HH:mm dd/MM/yyyy`      | 14:30 05/10/2025    |
| `HHmm yyyy MMM dd`      | 1430 2025 Oct 05    |
| `HH:mm yyyy MMM dd`     | 14:30 2025 Oct 05   |
| `HHmm MMM dd yyyy`      | 1430 Oct 05 2025    |
| `HH:mm MMM dd yyyy`     | 14:30 Oct 05 2025   |
| `HHmm dd MMM yyyy`      | 1430 05 Oct 2025    |
| `HH:mm dd MMM yyyy`     | 14:30 05 Oct 2025   |

### Date-Only Formats

| Format Pattern | Example     |
|----------------|-------------|
| `yyyy-MM-dd`   | 2025-10-05  |
| `yyyy/MM/dd`   | 2025/10/05  |
| `dd-MM-yyyy`   | 05-10-2025  |
| `dd/MM/yyyy`   | 05/10/2025  |
| `MMM dd yyyy`  | Oct 05 2025 |
| `dd MMM yyyy`  | 05 Oct 2025 |
| `yyyy MMM dd`  | 2025 Oct 05 |

### Time-Only Formats

| Format Pattern | Example  |
|----------------|----------|
| `HHmm`         | 1430     |
| `HH:mm`        | 14:30    |
| `hhmm a`       | 0230 PM  |
| `hhmma`        | 0230PM   |
| `hmm a`        | 230 PM   |
| `hmma`         | 230PM    |
| `hh:mm a`      | 02:30 PM |
| `hh:mma`       | 02:30PM  |
| `h:mm a`       | 2:30 PM  |
| `h:mma`        | 2:30PM   |
