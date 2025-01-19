import asyncio

class InsufficientFundsError(Exception):
    pass


class Account:
    def __init__(self, account_number: str, owner: str, initial_balance: float = 0.0):
        self.account_number = account_number
        self.owner = owner
        self.balance = initial_balance

    def deposit(self, amount: float):
        if amount <= 0:
            raise ValueError("Kwota musi być dodatnia.")
        self.balance += amount

    def withdraw(self, amount: float):
        if amount > self.balance:
            raise InsufficientFundsError("Niewystarczające saldo na koncie.")
        self.balance -= amount

    async def transfer(self, to_account, amount: float):
        if amount <= 0:
            raise ValueError("Kwota musi być dodatnia.")
        if self.balance < amount:
            raise InsufficientFundsError("Niewystarczające saldo na koncie.")
        # Symulacja opóźnienia sieciowego
        await asyncio.sleep(0.1)
        self.withdraw(amount)
        to_account.deposit(amount)


class Bank:
    def __init__(self):
        self.accounts = {}

    def create_account(self, account_number: str, owner: str, initial_balance: float = 0.0):
        if account_number in self.accounts:
            raise ValueError("Numer konta już istnieje.")
        account = Account(account_number, owner, initial_balance)
        self.accounts[account_number] = account
        return account

    def get_account(self, account_number: str):
        if account_number not in self.accounts:
            raise ValueError("Konto o tym numerze nie istnieje.")
        return self.accounts[account_number]

    async def process_transaction(self, transaction_func):
        await transaction_func()
