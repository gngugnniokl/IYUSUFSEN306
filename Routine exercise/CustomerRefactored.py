DISCOUNT_STANDARD = 0.10
DISCOUNT_PREMIUM  = 0.20


class Customer:
    def __init__(self, name, address, email, is_vip, order_type):
        self.name       = name
        self.address    = address
        self.email      = email
        self.is_vip     = is_vip
        self.order_type = order_type


def calculateOrderTotal(orders, count):
    if count < 0 or count > len(orders):
        raise ValueError("Invalid order count.")
    for amount in orders[:count]:
        if amount < 0:
            raise ValueError("Order amounts must be non-negative.")
    return sum(orders[:count])


def calculateDiscount(order_type):
    if order_type == 1:
        return DISCOUNT_STANDARD
    elif order_type == 2:
        return DISCOUNT_PREMIUM
    return 0.0


def applyDiscount(subtotal, discount_rate):
    return subtotal - subtotal * discount_rate


def buildGreetingMessage(customer, total):
    msg = f"Hello {customer.name} of {customer.address}, your total is {total:.2f}"
    if customer.is_vip:
        msg += " (VIP)"
    return msg


def printCustomerSummary(message):
    print(message)


def sendConfirmationEmail(customer, message):
    if customer.email:
        sendEmail(customer.email, message)


def processCustomer(customer, orders, count):
    subtotal      = calculateOrderTotal(orders, count)
    discount_rate = calculateDiscount(customer.order_type)
    total         = applyDiscount(subtotal, discount_rate)
    message       = buildGreetingMessage(customer, total)

    printCustomerSummary(message)
    sendConfirmationEmail(customer, message)

    return total


def sendEmail(address, body):
    print(f"[EMAIL → {address}]: {body}")


if __name__ == "__main__":
    c = Customer("Ada Lovelace", "Lagos HQ", "ada@example.com", True, 2)
    orders = [500.0, 300.0, 200.0]
    total = processCustomer(c, orders, count=3)
    print(f"Total: {total:.2f}")
