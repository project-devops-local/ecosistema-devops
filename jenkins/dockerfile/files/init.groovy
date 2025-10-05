#!groovy

import hudson.security.*
import hudson.security.csrf.*
import jenkins.model.*
import jenkins.security.s2m.AdminWhitelistRule
import org.jenkinsci.plugins.saml.*

def env = System.getenv()

def jenkins = Jenkins.getInstance()



// Configure Timezone

System.setProperty('org.apache.commons.jelly.tags.fmt.timeZone', 'America/Bogota')

jenkins.security.stapler.StaplerDispatchValidator.getInstance(Jenkins.instance.servletContext).cache.load()

def isValidString = { value ->
    if (value != null && value instanceof String && value.trim() != '') {
        return true
    }

    return false
}


// Set number of job executors

int num_executors = 0

if (isValidString(env.JENKINS_NUM_EXECUTORS)) {

    num_executors = env.JENKINS_NUM_EXECUTORS.toInteger()

}

jenkins.setNumExecutors(num_executors)



// Enable CSRF protection

jenkins.setCrumbIssuer(new DefaultCrumbIssuer(true))



// Disable old/unsafe agent protocols for security

jenkins.agentProtocols = ["JNLP4-connect", "Ping"] as Set



// // disabled CLI access over TCP listener (separate port)

// def p = jenkins.AgentProtocol.all()

// p.each { x ->

//     if (x.name?.contains("CLI")) {

//         println "Removing protocol ${x.name}"

//         p.remove(x)

//     }

// }



// // disable CLI access over /cli URL

// def removal = { lst ->

//     lst.each { x ->

//         if (x.getClass().name.contains("CLIAction")) {

//             println "Removing extension ${x.getClass().name}"

//             lst.remove(x)

//         }

//     }

// }



// removal(jenkins.getExtensionList(hudson.model.RootAction.class))

// removal(jenkins.actions)

// Nombre del nodo maestro
def nodeName = "Built-In Node"

// Obtener el nodo maestro
def node = Jenkins.instance.getNode(nodeName)

if (node) {
    // Configura la etiqueta
    def label = "master-only"
    def currentLabels = node.getAssignedLabels().collect { it.getName() }
    if (!currentLabels.contains(label)) {
        node.setLabelString((currentLabels + label).join(" "))
        println "Etiqueta '${label}' añadida al nodo ${nodeName}."
    } else {
        println "La etiqueta '${label}' ya está asignada al nodo ${nodeName}."
    }

    // Pone el nodo maestro fuera de línea para evitar su uso predeterminado
    def computer = node.getComputer()
    if (computer.isOnline()) {
        computer.setTemporarilyOffline(true, "Configuración inicial de Jenkins")
        println "El nodo ${nodeName} está temporalmente fuera de línea."
    }
} else {
    println "Nodo ${nodeName} no encontrado."
}



jenkins.save()