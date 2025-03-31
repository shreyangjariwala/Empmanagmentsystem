data class Project(val name: String, val description: String)

data class Employee(override var id: Int,override var name: String, var department: String,var age: Int,var projects: MutableList<Project>) : Person(id, name), Assignable {
    override fun getDetails(): String {
        return "ID: $id, Name: $name, Department: $department, Age: $age, Projects: ${projects.joinToString { it.name }}"
    }

    override fun assign(project: Project) {
        projects.add(project)
        println("Project ${project.name} assigned to $name")
    }
}

abstract class Person(open val id: Int, open var name: String) {
    abstract fun getDetails(): String
}

interface Assignable {
    fun assign(project: Project)
}

class Company {
    private val employees = mutableListOf<Employee>()
    private val departments = mutableListOf<String>()
    private val departmentEmployees = mutableMapOf<String, MutableList<Employee>>()

    fun addEmployee(employee: Employee) {
        employees.add(employee)
        println("Employee added: ${employee.getDetails()}")
    }

    fun addDepartment(department: String) {
        if (department !in departments) {
            departments.add(department)
            departmentEmployees[department] = mutableListOf()
            println("Department added: $department")
        } else {
            println("Department $department already exists.")
        }
    }

    fun assignEmployeeToDepartment(employeeId: Int, department: String) {
        val employee = employees.find { it.id == employeeId }
        if (employee != null && department in departments) {
            departmentEmployees[department]?.add(employee)
            employee.department = department
            println("Employee ${employee.name} assigned to $department")
        } else {
            println("Employee ID $employeeId or Department $department not found.")
        }
    }

    fun displayDetails() {
        println("\nCompany Details:")
        println("Departments: ${departments.joinToString()}")
        employees.forEach { println(it.getDetails()) }
    }
}

fun main() {
    val company = Company()
    company.addDepartment("HR")
    company.addDepartment("OPERATION")

    val project1 = Project("Website Redesign", "Revamp the company website")
    val project2 = Project("Security Audit", "Conduct a security audit for internal systems")

    val emp1 = Employee(1, "JOLLY", "", 30, mutableListOf(project1))
    val emp2 = Employee(2, "FRANKLIN", "", 25, mutableListOf(project2))

    company.addEmployee(emp1)
    company.addEmployee(emp2)

    company.assignEmployeeToDepartment(1, "HR")
    company.assignEmployeeToDepartment(2, "OPERATION")

    company.displayDetails()
}
