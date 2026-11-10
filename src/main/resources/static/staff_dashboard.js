const yearPicker = document.getElementById("academicYear");
const displayYear = document.getElementById("displayAcademicYear");

// ACADEMIC YEAR
if (yearPicker && displayYear) {
    yearPicker.addEventListener("change", () => {
        displayYear.textContent = yearPicker.value;
    });
}

// DASHBOARD STATISTICS
async function loadDashboardStats() {
    try {
        const response = await fetch("/api/staff/applications/stats");

        if (!response.ok) {
            throw new Error("Failed to load dashboard statistics.");
        }

        const stats = await response.json();

        document.getElementById("totalStudents").textContent =
            stats.totalStudents;

        document.getElementById("enrolledStudents").textContent =
            stats.enrolledStudents;

        document.getElementById("pendingApplications").textContent =
            stats.pendingApplications;

        document.getElementById("rejectedApplications").textContent =
            stats.rejectedApplications;

    } catch (error) {
        console.error("Dashboard Statistics Error:", error);
    }
}

// RECENT STUDENTS
async function loadRecentStudents() {
    const tableBody = document.getElementById("recentStudentsBody");

    try {
        const response = await fetch("/api/staff/applications/recent");

        if (!response.ok) {
            throw new Error("Failed to load recent students.");
        }

        const students = await response.json();

        tableBody.replaceChildren();

        if (students.length === 0) {
            const row = document.createElement("tr");
            const cell = document.createElement("td");

            cell.colSpan = 6;
            cell.className = "empty-state";
            cell.textContent = "No student records available yet.";

            row.appendChild(cell);
            tableBody.appendChild(row);
            return;
        }

        students.forEach(student => {
            const row = document.createElement("tr");

            const values = [
                student.studentId ?? "N/A",
                student.fullName ?? "N/A",
                student.studentType ?? "N/A",
                student.courseId ?? "N/A",
                student.yearLevel ?? "N/A",
                student.status ?? "PENDING"
            ];

            values.forEach(value => {
                const cell = document.createElement("td");
                cell.textContent = value;
                row.appendChild(cell);
            });

            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error("Recent Students Error:", error);

        tableBody.replaceChildren();

        const row = document.createElement("tr");
        const cell = document.createElement("td");

        cell.colSpan = 6;
        cell.textContent = "Unable to load student records.";

        row.appendChild(cell);
        tableBody.appendChild(row);
    }
}

// LOAD DASHBOARD DATA
loadDashboardStats();
loadRecentStudents();