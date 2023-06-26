import groovy.time.TimeCategory
import com.cloudbees.groovy.cps.NonCPS

class CalcDateParams {
    def base = new Date()
    int days = 0
    int hours = 0
    int minutes = 0
}

@NonCPS
def min(Map args) {
    def date = ''

    CalcDateParams params = args ?: [:]

    use (TimeCategory) {
        date = params.base - params.days.days - params.hours.hours - params.minutes.minutes
    }

    return date
}

@NonCPSse
def max(Map args) {
    def date = ''

    CalcDateParams params = args ?: [:]

    use (TimeCategory) {
        date = params.base + params.days.days + params.hours.hours + params.minutes.minutes
    }

    return date
}
