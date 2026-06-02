# REFLECTION.md

## 1. How did you achieve functional cohesion? Which routines did you extract?

I broke the original function into six smaller ones, each doing just one thing:

- `calculateOrderTotal` – adds up the orders
- `calculateDiscount` – figures out the discount rate based on order type
- `applyDiscount` – applies the discount to get the final total
- `buildGreetingMessage` – puts together the message string
- `printCustomerSummary` – prints it
- `sendConfirmationEmail` – sends the email if there's an address

I also made a `Customer` class so I didn't have to pass seven separate parameters everywhere.

## 2. What parameter passing issues did you encounter?

The original had `d = total` at the end, which does nothing. Since `d` is a primitive in Java, it's passed by value, so that assignment only changes the local copy — the caller never sees it. I fixed it by just returning `total` from `processCustomer`.

## 3. How would the `d` update behave differently with pass-by-value-result?

With value-result, the local copy of `d` gets written back to the caller's variable when the function returns, so `d = total` would actually work as intended. It's different from pass-by-reference where changes are visible immediately during the call, and obviously different from pass-by-value where nothing gets written back at all.
