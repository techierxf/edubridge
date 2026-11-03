
const modal = document.getElementById("addStudentModal");
const openButton = document.getElementById("openAddStudent");
const closeButton = document.getElementById("closeAddStudent");
const cancelButton = document.getElementById("cancelAddStudent");
const form = document.getElementById("addStudentForm");
const formMessage = document.getElementById("formMessage");
const yearPicker = document.getElementById("academicYear");
const displayYear = document.getElementById("displayAcademicYear");

// OPEN MODAL
function openModal() {
    modal.hidden = false;
    formMessage.textContent = "";

    const firstInput = form.querySelector('input[name="full_name"]');
    if (firstInput) firstInput.focus();
}

// CLOSE MODAL
function closeModal() {
    modal.hidden = true;
    formMessage.textContent = "";
    openButton.focus();
}

openButton.addEventListener("click", openModal);
closeButton.addEventListener("click", closeModal);
cancelButton.addEventListener("click", closeModal);

modal.addEventListener("click", (event) => {
    if (event.target === modal) closeModal();
});

document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && !modal.hidden) closeModal();
});

// ACADEMIC YEAR
yearPicker.addEventListener("change", () => {
    displayYear.textContent = yearPicker.value;
});

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

// LOAD STATISTICS WHEN PAGE OPENS
loadDashboardStats();

// DEMO-ONLY ADD STUDENT HANDLER
form.addEventListener("submit", (event) => {
    event.preventDefault();

    formMessage.textContent =
        "Preview only: this form is not connected to the database, so no student was saved.";
});

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

        tableBody.innerHTML =
            '<tr><td colspan="6">Unable to load student records.</td></tr>';
    }
}

loadRecentStudents();

